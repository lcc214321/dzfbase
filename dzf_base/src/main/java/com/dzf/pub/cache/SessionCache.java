package com.dzf.pub.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.dzf.framework.comn.IOUtils;
import com.dzf.model.pub.DZFSessionListVO;
import com.dzf.model.pub.DZFSessionVO;
import com.dzf.pub.IGlobalConstants;
import com.dzf.pub.StringUtil;
import com.dzf.pub.SessionRedis.IRedisSessionCallback;
import com.dzf.pub.SessionRedis.SessionRedisClient;
import com.dzf.pub.session.DzfSessionTool;

public class SessionCache {
	
	private static SessionCache sc = new SessionCache();

	private static ConcurrentHashMap<String, DZFSessionListVO> m_hmSessionByUserID = new ConcurrentHashMap<String, DZFSessionListVO>();
	private static ConcurrentHashMap<String, DZFSessionListVO> m_hmSession = new ConcurrentHashMap<String, DZFSessionListVO>();
	private static ConcurrentHashMap<String, String> m_hmVerify = new ConcurrentHashMap<String, String>();
	private Logger log = Logger.getLogger(this.getClass());

	private DZFSessionListVO getSessionByRedis(Jedis jedis, final String strUUID) {
		DZFSessionListVO obj = null;
		try {

		
			byte[] bs = jedis.get(strUUID.getBytes());

			if (bs == null) {
				return null;
			}
			obj = (DZFSessionListVO) IOUtils.getObject(bs, new SessionSerializable());
		} catch (Exception e) {
			log.error("缓存服务器连接未成功。");
			return null;
		}
		return obj;
	}
	/**
	 * 同步session， 不同步的话redis 的session可能会掉线 
	 * 同步是针对redis进行的！！
	 * @param session
	 */
	public void synchronizeSession(HttpSession session)
	{
		if (SessionRedisClient.getInstance().getEnabled() == false)
		{
			return;
		}
		String pk_user = (String)session.getAttribute(IGlobalConstants.login_user);
		
		if (StringUtil.isEmptyWithTrim(pk_user))
		{
			return;
		}
		String uuid = (String)session.getAttribute(IGlobalConstants.uuid);
		String appid = (String)session.getAttribute(IGlobalConstants.appid);
		
		//先同步uuid做key的缓存
		
		DZFSessionListVO dzfSessionListVo = getByUUID(uuid);
		List<DZFSessionVO> listDzfSessionVo = null;
		int iSecond = -1;
		
		DZFSessionVO newSessionVo = DzfSessionTool.createSession(session);
		DZFSessionVO oldSessionVO = null;
		if (dzfSessionListVo != null)
		{
			listDzfSessionVo = dzfSessionListVo.getListSessionVO();
			for (DZFSessionVO svo : listDzfSessionVo)
			{
				if (svo.getAppid().equals(appid))
				{
					oldSessionVO = svo;
					break;
				}
			}
		}

		if (oldSessionVO != null && oldSessionVO.getLasttime() < newSessionVo.getLasttime())
		{
			listDzfSessionVo.remove(oldSessionVO);
			listDzfSessionVo.add(newSessionVo);
			
			iSecond = session.getMaxInactiveInterval() - (int)((System.currentTimeMillis() - newSessionVo.getLasttime()) / 1000);
			add(uuid, dzfSessionListVo, iSecond);
					
		}
		
		//更新pk_user做key的缓存
		
		oldSessionVO = null;
		
		dzfSessionListVo = getByUserID(pk_user);
		if (dzfSessionListVo != null)
		{
			listDzfSessionVo = dzfSessionListVo.getListSessionVO();
			for (DZFSessionVO svo : listDzfSessionVo)
			{
				if (svo.getAppid().equals(appid) && svo.getUuid().equals(uuid))
				{
					oldSessionVO = svo;
					break;
				}
			}
		}
		
		if (oldSessionVO != null && oldSessionVO.getLasttime() < newSessionVo.getLasttime())
		{
			listDzfSessionVo.remove(oldSessionVO);
			listDzfSessionVo.add(newSessionVo);
			
			String realKey = "dzfsso" + pk_user;
			iSecond = session.getMaxInactiveInterval() - (int)((System.currentTimeMillis() - newSessionVo.getLasttime()) / 1000);
			add(realKey, dzfSessionListVo, iSecond);
		}
		
		
		
	}
	
	public void addSession(HttpSession session)
	{
		String strUUID = (String)session.getAttribute(IGlobalConstants.uuid);
	
		if (StringUtil.isEmptyWithTrim(strUUID))
		{
			return;
		}
		
		DZFSessionVO newRedissessionvo = DzfSessionTool.createSession(session);
		
		addByUUID(newRedissessionvo, session.getMaxInactiveInterval());
		
		addByUserID(newRedissessionvo, session.getMaxInactiveInterval());

	}
	/**
	 * 使用dzfsessionvo加入session信息到redis服务器
	 * @param dzfsession
	 * @param iMaxInactiveInterval
	 */
	public void addSession(DZFSessionVO dzfsession, int iMaxInactiveInterval)
	{
		
		if (dzfsession == null || StringUtil.isEmptyWithTrim(dzfsession.getUuid()))
		{
			return;
		}
		
		
		addByUUID(dzfsession, iMaxInactiveInterval);
		
		addByUserID(dzfsession, iMaxInactiveInterval);

	}
	
	/**
	 * 写验证码
	 * @param uuid
	 * @param verify
	 */
	public void addVerify(final String uuid, final String verify)
	{
		final String newKey = "verify" + uuid;
		
		if (SessionRedisClient.getInstance().getEnabled() == false)
		{
			m_hmVerify.put(newKey, verify);
		}
		else
		{
			SessionRedisClient.getInstance().exec(new IRedisSessionCallback() {
				@Override
				public Object exec(Jedis jedis) {
	
					
					ReentrantLock lock = SessionLock.getInstance().get(newKey);
					try {
						//加锁
						lock.lock();
						//默认5分钟
						jedis.setex(newKey.getBytes(), 300, verify.getBytes("utf-8"));
	
					} catch (Exception e) {
						log.error("缓存服务器连接未成功。", e);
					} finally {
						if (lock != null)
							lock.unlock();
					}
					return null;
				}
			});
		}
	}
	/**
	 * 用uuid读验证码
	 * @param uuid
	 * @return
	 */
	public String getVerify(String uuid)
	{
		String verify = null;
		final String newKey = "verify" + uuid;
		if (SessionRedisClient.getInstance().getEnabled() == false)
		{
			verify = m_hmVerify.get(newKey);
			if (verify != null)
			{
				m_hmVerify.remove(newKey);
			}
		}
		else
		{
			verify = (String) SessionRedisClient.getInstance().exec(new IRedisSessionCallback() {

				@Override
				public Object exec(Jedis jedis) {
					String ret = null;
					if (jedis == null)	//没有缓存服务器，查不到值
					{
						return null;
					}
					ReentrantLock lock = SessionLock.getInstance().get(newKey);	
					try {
						lock.lock();
						byte[] bs = jedis.get(newKey.getBytes());
						if (bs == null) {
							return null;
						}
						jedis.del(newKey.getBytes());
						ret = new String(bs, "utf-8");
					} catch (Exception e) {
						log.error("缓存服务器连接未成功。", e);
					} finally {
						if (lock != null)
							lock.unlock();
					}

	
					return ret;
				}
			});
		}
		return verify;
	}
	private DZFSessionVO createSession(HttpSession session)
	{
		DZFSessionVO dzfsession = new DZFSessionVO();
		dzfsession.setAppid((String)session.getAttribute(IGlobalConstants.appid));
		dzfsession.setRemoteIp((String)session.getAttribute(IGlobalConstants.remote_address));
		dzfsession.setDate((String)session.getAttribute(IGlobalConstants.login_date));
		dzfsession.setDzfMap((Set<Integer>)session.getAttribute(IGlobalConstants.POWER_MAP));
		dzfsession.setLasttime(session.getLastAccessedTime());
		dzfsession.setPk_corp((String)session.getAttribute(IGlobalConstants.login_corp));
		dzfsession.setPk_user((String)session.getAttribute(IGlobalConstants.login_user));
		dzfsession.setSessionid(session.getId());
		
		//小微无忧
		dzfsession.setXwwy_sessionid((String)session.getAttribute("xwwy_sessionid"));
		dzfsession.setToken((String)session.getAttribute("token"));
		
		return dzfsession;
	}
	/**
	 * DZFSessionVO 中的数据按照appid分组
	 * @param m
	 * @param iExpiredSecond
	 */
	private void addByUserID(final DZFSessionVO m, int iExpiredSecond) {
		final String userid = m.getPk_user();
		
		
		DZFSessionListVO dzfSessionListVo = getByUserID(userid);
		List<DZFSessionVO> listDzfSessionVO = null;
		List<DZFSessionVO> listNewDzfSessionVO = new ArrayList<DZFSessionVO>();
		listNewDzfSessionVO.add(m);
		
		long latesttime = m.getLasttime();
		if (dzfSessionListVo != null)
		{
			listDzfSessionVO = dzfSessionListVo.getListSessionVO();
			
			for (DZFSessionVO sessionvo : listDzfSessionVO)
			{
				if (sessionvo.getLasttime() > latesttime)
				{
					latesttime = sessionvo.getLasttime();
				}
				if (sessionvo.getAppid().equals(m.getAppid()) == false)
				{
					listNewDzfSessionVO.add(sessionvo);
					
				}
			}
		}
		else
		{
			dzfSessionListVo = new DZFSessionListVO();
		}
		dzfSessionListVo.setListSessionVO(listNewDzfSessionVO);
		
		
		String realKey = "dzfsso" + userid;
		
		if (SessionRedisClient.getInstance().getEnabled() == false)
		{
			m_hmSessionByUserID.put(realKey, dzfSessionListVo);
		}
		else
		{

			int iSecond = iExpiredSecond - (int)(System.currentTimeMillis() - latesttime) / 1000;
			
			add(realKey, dzfSessionListVo, iSecond);
		}
	}
	private void add(final String key, final DZFSessionListVO m, final int iExpiredSecond)
	{
		SessionRedisClient.getInstance().exec(new IRedisSessionCallback() {
			@Override
			public Object exec(Jedis jedis) {

				
				ReentrantLock lock = SessionLock.getInstance().get(key);
				try {
					//加锁
					lock.lock();
					//带失效时间
					if (iExpiredSecond > 0)
					{
						jedis.setex(key.getBytes(), iExpiredSecond, IOUtils.getBytes(m, new SessionSerializable()));
					}
					else
					{
						//已过期，删掉
						jedis.del(key.getBytes());
					}
				} catch (Exception e) {
					log.error("缓存服务器连接未成功。", e);
				} finally {
					if (lock != null)
						lock.unlock();
				}
				return null;
			}
		});
	}
	/**
	 * DZFSessionVO 中的值按照appid分组
	 * @param m
	 * @param iExpiredSecond
	 */
	private void addByUUID(final DZFSessionVO m, int iExpiredSecond) {
		final String strUUID = m.getUuid();
		
		DZFSessionListVO dzfSessionListVo = getByUUID(m.getUuid());
		List<DZFSessionVO> listDzfSessionVO = null;
		List<DZFSessionVO> listNewDzfSessionVO = new ArrayList<DZFSessionVO>();
		listNewDzfSessionVO.add(m);
		
		long latesttime = m.getLasttime();
		if (dzfSessionListVo != null)
		{
			listDzfSessionVO = dzfSessionListVo.getListSessionVO();

			for (DZFSessionVO sessionvo : listDzfSessionVO)
			{
				if (sessionvo.getLasttime() > latesttime)
				{
					latesttime = sessionvo.getLasttime();
				}
				if (sessionvo.getAppid().equals(m.getAppid()) == false)
				{
					listNewDzfSessionVO.add(sessionvo);
				}
			}
		}
		else
		{
			dzfSessionListVo = new DZFSessionListVO();
		}
		dzfSessionListVo.setListSessionVO(listNewDzfSessionVO);
	
		if (SessionRedisClient.getInstance().getEnabled() == false)
		{
			m_hmSession.put(strUUID, dzfSessionListVo);
		}
		else
		{			
			int iSecond = iExpiredSecond - (int)(System.currentTimeMillis() - latesttime) / 1000;
			
			add(strUUID, dzfSessionListVo, iSecond);
		}
	}
	private void removeByUserID(final String uuid, final String pk_user, String appid, int iExpiredSecond) {
		
		DZFSessionListVO sessionlistvo = getByUserID(pk_user);
		final String realKey = "dzfsso" + pk_user;
		if (sessionlistvo != null)
		{
			List<DZFSessionVO> listSessionVO = sessionlistvo.getListSessionVO();
			List<DZFSessionVO> listDelete = new ArrayList<DZFSessionVO>();
			for (DZFSessionVO svo : listSessionVO)
			{
				if (svo.getUuid().equals(uuid) && appid.equals(svo.getAppid()))
				{
					listDelete.add(svo);
				}
			}
			for (DZFSessionVO svo : listDelete)
			{
				listSessionVO.remove(svo);
			}
			if (listSessionVO.size() > 0)
			{
				sessionlistvo.setListSessionVO(listSessionVO); 
				if (SessionRedisClient.getInstance().getEnabled() == false)
				{
					m_hmSessionByUserID.put(realKey, sessionlistvo);
				}
				else
				{
					add(realKey, sessionlistvo, iExpiredSecond);
				}
			}
			else
			{
				if (SessionRedisClient.getInstance().getEnabled() == false)
				{
					m_hmSessionByUserID.remove(realKey);
				}
				else
				{
					SessionRedisClient.getInstance().exec(new IRedisSessionCallback() {
						
						@Override
						public Object exec(Jedis jedis) {
							try {
								jedis.del(realKey.getBytes());
								
							} catch (Exception e) {
			
								log.error("缓存服务器连接未成功。", e);
								return null;
							}
							return null;
						}
					});
				}
			}
		}
		
		
	}
	public void removeByUUID(final String strUUID, final String pk_user, final String appid, int iExpiredSecond) {


		if (pk_user != null)	//把pk_user登录信息中包含当前uuid的信息全部删除
		{
			removeByUserID(strUUID, pk_user, appid, iExpiredSecond);
		}
		
		DZFSessionListVO sessionlistvo = getByUUID(strUUID);
		if (sessionlistvo == null)
		{
			return;
		}
		List<DZFSessionVO> listSessionVO = sessionlistvo.getListSessionVO();
		List<DZFSessionVO> listDelete = new ArrayList<DZFSessionVO>();
		for (DZFSessionVO svo : listSessionVO)
		{
			if (appid.equals(svo.getAppid()))
			{
				listDelete.add(svo);
			}
		}
		for (DZFSessionVO svo : listDelete)
		{
			listSessionVO.remove(svo);
		}
		
		if (SessionRedisClient.getInstance().getEnabled() == false)
		{
			if (listSessionVO.size() > 0)
			{
				m_hmSession.put(strUUID, sessionlistvo);
			}
			else
			{
				m_hmSession.remove(strUUID);
			}
		}
		else
		{
			if (listSessionVO.size() > 0)
			{
				add(strUUID, sessionlistvo, iExpiredSecond);
			}
			else
			{
				SessionRedisClient.getInstance().exec(new IRedisSessionCallback() {
		
					@Override
					public Object exec(Jedis jedis) {
						try {
							jedis.del(strUUID.getBytes());
							
						} catch (Exception e) {
		
							log.error("缓存服务器连接未成功。", e);
							return null;
						}
						return null;
					}
				});
			}
		}
	}
	public DZFSessionListVO getByUserID(final String userid) {
		if (userid == null) {
			return null;
		}
		DZFSessionListVO sessionListVo = null; 
		
		final String realKey = "dzfsso" + userid;

		if (SessionRedisClient.getInstance().getEnabled() == false)
		{
			sessionListVo = m_hmSessionByUserID.get(realKey);
		}
		else
		{
			sessionListVo = (DZFSessionListVO) SessionRedisClient.getInstance().exec(new IRedisSessionCallback() {

				@Override
				public Object exec(Jedis jedis) {
					if (jedis == null)	//没有缓存服务器，查不到值
					{
						return null;
					}

					DZFSessionListVO sessionlistvo = getSessionByRedis(jedis, realKey);
					if (sessionlistvo == null) {
						ReentrantLock lock = SessionLock.getInstance().get(realKey);	//注意，lock的id，与getSessionByRedis 调用的不一样
						try {
							lock.lock();
							sessionlistvo = getSessionByRedis(jedis, realKey);
	
						} finally {
							if (lock != null)
								lock.unlock();
						}
					}
	
					return sessionlistvo;
				}
			});
		}
		return sessionListVo;
	}
	public DZFSessionVO getByUserID(String userid, String appid) {

		DZFSessionListVO sessionListVo = getByUserID(userid);
		
		
		if (sessionListVo != null)
		{
			for (DZFSessionVO svo : sessionListVo.getListSessionVO())
			{
				if (svo.getAppid().equals(appid))
				{
					return svo;
				}
			}
		}
		return null;
	}
	public DZFSessionListVO getByUUID(final String strUUID) {
		if (strUUID == null) {
			return null;
		}
		DZFSessionListVO sessionListVo = null; 

		if (SessionRedisClient.getInstance().getEnabled() == false)
		{
			sessionListVo = m_hmSession.get(strUUID);
		}
		else
		{
			sessionListVo = (DZFSessionListVO) SessionRedisClient.getInstance().exec(new IRedisSessionCallback() {

				@Override
				public Object exec(Jedis jedis) {
					if (jedis == null)	//没有缓存服务器，查不到值
					{
						return null;
					}
					DZFSessionListVO sessionlistvo = getSessionByRedis(jedis, strUUID);
					if (sessionlistvo == null) {
						ReentrantLock lock = SessionLock.getInstance().get(strUUID);	//注意，lock的id，与getSessionByRedis 调用的不一样
						try {
							lock.lock();
							sessionlistvo = getSessionByRedis(jedis, strUUID);
	
						} finally {
							if (lock != null)
								lock.unlock();
						}
					}
	
					return sessionlistvo;
				}
			});
		}
		
		return sessionListVo;
	}
	public DZFSessionVO getByUUID(String strUUID, String appid) {

		DZFSessionListVO sessionListVo = getByUUID(strUUID);

		if (sessionListVo != null)
		{
			for (DZFSessionVO svo : sessionListVo.getListSessionVO())
			{
				if (svo.getAppid().equals(appid))
				{
					return svo;
				}
			}
		}
		return null;
	}
	private SessionCache() {

	}

	public static SessionCache getInstance() {

		return sc;
	}
}
