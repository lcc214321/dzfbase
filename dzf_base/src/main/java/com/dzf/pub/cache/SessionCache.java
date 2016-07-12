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
import com.dzf.model.pub.DZFSessionVO;
import com.dzf.pub.IGlobalConstants;
import com.dzf.pub.StringUtil;
import com.dzf.pub.Redis.IRedisCallback;
import com.dzf.pub.Redis.RedisClient;
import com.dzf.pub.session.DzfSessionTool;

public class SessionCache {
	
	private static SessionCache sc = new SessionCache();

	private static ConcurrentHashMap<String, DZFSessionVO> m_hmSessionByUserID = new ConcurrentHashMap<String, DZFSessionVO>();
	private static ConcurrentHashMap<String, DZFSessionVO> m_hmSession = new ConcurrentHashMap<String, DZFSessionVO>();
	private Logger log = Logger.getLogger(this.getClass());

	private DZFSessionVO getSessionByRedis(Jedis jedis, final String strUUID) {
		DZFSessionVO obj = null;
		try {

		
			byte[] bs = jedis.get(strUUID.getBytes());

			if (bs == null) {
				return null;
			}
			obj = (DZFSessionVO) IOUtils.getObject(bs, new SessionSerializable());
		} catch (Exception e) {
			log.error("缓存服务器连接未成功。");
			return null;
		}
		return obj;
	}
//	public void addSession(HttpSession session)
//	{
//		String pk_user = (String)session.getAttribute(IGlobalConstants.login_user);
//		
//		if (StringUtil.isEmptyWithTrim(pk_user))
//		{
//			return;
//		}
//		boolean isOld = false;	//session是否比缓存上的还旧
//		DZFSession[] sessions = null;
//		long latestTime = session.getLastAccessedTime();
//
//		List<DZFSession> listDzfSession = new ArrayList<DZFSession>();
//		
//		String appid = (String)session.getAttribute(IGlobalConstants.appid);
//
//		
//		DZFSessionVO sessionvo = SessionCache.getInstance().get(pk_user);
//		
//		if (sessionvo != null)
//		{
//			sessions = sessionvo.getSessions();
//			for (DZFSession s : sessions)
//			{
//				//最后操作时间更新
//				if ((s.getAppid() == null ? "" : s.getAppid()).equals(appid == null ? "" : appid) == false)
//				{
//					if (System.currentTimeMillis() - s.getLasttime() < session.getMaxInactiveInterval() * 1000)//超时的也删掉
//						listDzfSession.add(s);
//				}
//				else
//				{
//					//即使是相同的app，也要判断新add过来的时间是否是最新的，
//					if (s.getLasttime() > session.getLastAccessedTime())
//					{
//						isOld = true;
//						break;
//					}
//				}
//				//找所有客户端的最后操作时间（重复登录场景)
//				if (s.getLasttime() > latestTime)
//				{
//					latestTime = s.getLasttime();
//				}
//
//			}
//			
//		}
//		else
//		{
//			sessionvo = new DZFSessionVO();
//		}
//		
//		if (isOld)	//拿来更新的session比redis上已被其他服务器更新的旧，不做处理。
//		{
//			return;
//		}
//		
//		DZFSession newRedissession = createSession(session);
//		listDzfSession.add(newRedissession);
//
//		sessionvo.setSessions(listDzfSession.toArray(new DZFSession[0]));
//		
//		add(pk_user, sessionvo, (int)(session.getMaxInactiveInterval() - (System.currentTimeMillis() - latestTime) / 1000));
//	}
	
	public void addSession(HttpSession session)
	{
		String strUUID = (String)session.getAttribute(IGlobalConstants.uuid);
		String appid = (String)session.getAttribute(IGlobalConstants.appid);
		String userid = (String)session.getAttribute(IGlobalConstants.login_user);
		
		if (StringUtil.isEmptyWithTrim(strUUID))
		{
			return;
		}
		
		DZFSessionVO newRedissessionvo = DzfSessionTool.createSession(session);
		//expired second
		int iSecond = session.getMaxInactiveInterval() - (int)(System.currentTimeMillis() - newRedissessionvo.getLasttime()) / 1000;
		
		addByUUID(newRedissessionvo, iSecond);
		
		DZFSessionVO oldResisSessionVO = getByUserID(userid, appid);
		if (oldResisSessionVO == null || oldResisSessionVO.getLasttime() < newRedissessionvo.getLasttime())
		{
			addByUserID(newRedissessionvo, iSecond);
		}
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
	private void addByUserID(final DZFSessionVO m, final int iExpiredSecond) {
		final String userid = m.getPk_user();
		String appid = m.getAppid();
		final String realKey = appid + userid;
		if (RedisClient.getInstance().getEnabled() == false)
		{
			m_hmSessionByUserID.put(realKey, m);
		}
		else
		{
			RedisClient.getInstance().exec(new IRedisCallback() {
				@Override
				public Object exec(Jedis jedis) {

					
					ReentrantLock lock = SessionLock.getInstance().get(realKey);
					try {
						//加锁
						lock.lock();
	
						jedis.set(realKey.getBytes(), IOUtils.getBytes(m, new SessionSerializable()));
						jedis.expire(realKey, iExpiredSecond);		//失效时间
						
	
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
	private void addByUUID(final DZFSessionVO m, final int iExpiredSecond) {
		final String strUUID = m.getUuid();
		
		if (RedisClient.getInstance().getEnabled() == false)
		{
			m_hmSession.put(strUUID, m);
		}
		else
		{
			RedisClient.getInstance().exec(new IRedisCallback() {
				@Override
				public Object exec(Jedis jedis) {
				
					
					ReentrantLock lock = SessionLock.getInstance().get(strUUID);
					try {
						//加锁
						lock.lock();
	
						jedis.set(strUUID.getBytes(), IOUtils.getBytes(m, new SessionSerializable()));
						jedis.expire(strUUID, iExpiredSecond);		//失效时间
						
	
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
	public void removeByUserID(final String pk_user, final String appid) {
		
		DZFSessionVO sessionvo = getByUserID(pk_user, appid);
		
		if (sessionvo != null)
		{
			final String realKey = appid + pk_user;
			if (RedisClient.getInstance().getEnabled() == false)
			{
				m_hmSessionByUserID.remove(realKey);
			}
			else
			{
				RedisClient.getInstance().exec(new IRedisCallback() {
		
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
	public void removeByUUID(final String strUUID) {

		DZFSessionVO sessionvo = getByUUID(strUUID);
		if (sessionvo != null)
		{
			if (RedisClient.getInstance().getEnabled() == false)
			{
				m_hmSession.remove(strUUID);
			}
			else
			{
				RedisClient.getInstance().exec(new IRedisCallback() {
		
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
	public DZFSessionVO getByUserID(final String userid, final String appid) {
		if (userid == null) {
			return null;
		}
		final String realKey = appid + userid;
		DZFSessionVO sessionvo = null;
		if (RedisClient.getInstance().getEnabled() == false)
		{
			sessionvo = m_hmSessionByUserID.get(realKey);
		}
		else
		{
			sessionvo = (DZFSessionVO) RedisClient.getInstance().exec(new IRedisCallback() {

				@Override
				public Object exec(Jedis jedis) {
					if (jedis == null)	//没有缓存服务器，查不到值
					{
						return null;
					}

					DZFSessionVO sessionvo = getSessionByRedis(jedis, realKey);
					if (sessionvo == null) {
						ReentrantLock lock = SessionLock.getInstance().get(realKey);	//注意，lock的id，与getSessionByRedis 调用的不一样
						try {
							lock.lock();
							sessionvo = getSessionByRedis(jedis, realKey);
	
						} finally {
							if (lock != null)
								lock.unlock();
						}
					}
	
					return sessionvo;
				}
			});
		}

		return sessionvo;
	}
	public DZFSessionVO getByUUID(final String strUUID) {
		if (strUUID == null) {
			return null;
		}
		DZFSessionVO sessionvo = null;
		if (RedisClient.getInstance().getEnabled() == false)
		{
			sessionvo = m_hmSession.get(strUUID);
		}
		else
		{
			sessionvo = (DZFSessionVO) RedisClient.getInstance().exec(new IRedisCallback() {

				@Override
				public Object exec(Jedis jedis) {
					if (jedis == null)	//没有缓存服务器，查不到值
					{
						return null;
					}
					DZFSessionVO sessionvo = getSessionByRedis(jedis, strUUID);
					if (sessionvo == null) {
						ReentrantLock lock = SessionLock.getInstance().get(strUUID);	//注意，lock的id，与getSessionByRedis 调用的不一样
						try {
							lock.lock();
							sessionvo = getSessionByRedis(jedis, strUUID);
	
						} finally {
							if (lock != null)
								lock.unlock();
						}
					}
	
					return sessionvo;
				}
			});
		}

		return sessionvo;
	}
	private SessionCache() {

	}

	public static SessionCache getInstance() {

		return sc;
	}
}
