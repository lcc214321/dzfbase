package com.dzf.pub.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
import com.dzf.pub.session.DZFSession;

public class SessionCache {
	
	private static SessionCache sc = new SessionCache();


	private Logger log = Logger.getLogger(this.getClass());

	private DZFSessionVO getSessionByRedis(Jedis jedis, final String userid) {
		DZFSessionVO obj = null;
		try {
			String realKey = "dzfsso" + userid;
			byte[] bs = jedis.get(realKey.getBytes());

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
	public void addSession(HttpSession session)
	{
		String pk_user = (String)session.getAttribute(IGlobalConstants.login_user);
		
		if (StringUtil.isEmptyWithTrim(pk_user))
		{
			return;
		}
		boolean isOld = false;	//session是否比缓存上的还旧
		DZFSession[] sessions = null;
		long latestTime = session.getLastAccessedTime();

		List<DZFSession> listDzfSession = new ArrayList<DZFSession>();
		
		String appid = (String)session.getAttribute(IGlobalConstants.appid);

		
		DZFSessionVO sessionvo = SessionCache.getInstance().get(pk_user);
		
		if (sessionvo != null)
		{
			sessions = sessionvo.getSessions();
			for (DZFSession s : sessions)
			{
				//最后操作时间更新
				if ((s.getAppid() == null ? "" : s.getAppid()).equals(appid == null ? "" : appid) == false)
				{
					if (System.currentTimeMillis() - s.getLasttime() < session.getMaxInactiveInterval() * 1000)//超时的也删掉
						listDzfSession.add(s);
				}
				else
				{
					//即使是相同的app，也要判断新add过来的时间是否是最新的，
					if (s.getLasttime() > session.getLastAccessedTime())
					{
						isOld = true;
						break;
					}
				}
				//找所有客户端的最后操作时间（重复登录场景)
				if (s.getLasttime() > latestTime)
				{
					latestTime = s.getLasttime();
				}

			}
			
		}
		else
		{
			sessionvo = new DZFSessionVO();
		}
		
		if (isOld)	//拿来更新的session比redis上已被其他服务器更新的旧，不做处理。
		{
			return;
		}
		
		DZFSession newRedissession = createSession(session);
		listDzfSession.add(newRedissession);

		sessionvo.setSessions(listDzfSession.toArray(new DZFSession[0]));
		
		add(pk_user, sessionvo, (int)(session.getMaxInactiveInterval() - (System.currentTimeMillis() - latestTime) / 1000));
	}
	private DZFSession createSession(HttpSession session)
	{
		DZFSession dzfsession = new DZFSession();
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
	private void add(final String key, final DZFSessionVO m, final int iExpiredSecond) {
		RedisClient.getInstance().exec(new IRedisCallback() {
			@Override
			public Object exec(Jedis jedis) {
				String realKey = "dzfsso" + key;
				
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

	public void remove(final String pk_user, String appid, int iExpiredSecond) {

		long lLatestTime = 0;
		DZFSessionVO sessionvo = get(pk_user);
		if (sessionvo != null)
		{
			//把指定appid的客户端移除
			List<DZFSession> listsession = new ArrayList<DZFSession>();
			for (DZFSession session : sessionvo.getSessions())
			{
				if (session.getLasttime() > lLatestTime)
				{
					lLatestTime = session.getLasttime();
				}
				if ((appid == null ? "" : appid).equals(session.getAppid() == null ? "" : session.getAppid()) == false)
				{
					listsession.add(session);
				}
			}
			
			if (listsession.size() == 0)
			{
				RedisClient.getInstance().exec(new IRedisCallback() {
		
					@Override
					public Object exec(Jedis jedis) {
						try {
							String realKey = "dzfsso" + pk_user;
							jedis.del(realKey.getBytes());
							
						} catch (Exception e) {
		
							log.error("缓存服务器连接未成功。", e);
							return null;
						}
						return null;
					}
				});
			}
			else
			{
				//重新计算生命周期秒数
				int iSecond = iExpiredSecond - (int)((System.currentTimeMillis() - lLatestTime) / 1000);
				
				sessionvo.setSessions(listsession.toArray(new DZFSession[0]));
				add(pk_user, sessionvo, iSecond);
			}
		}
	}

	public DZFSessionVO get(final String userid) {
		if (userid == null) {
			return null;
		}
		DZFSessionVO sessionvo = (DZFSessionVO) RedisClient.getInstance().exec(new IRedisCallback() {

			@Override
			public Object exec(Jedis jedis) {
				DZFSessionVO sessionvo = getSessionByRedis(jedis, userid);
				if (sessionvo == null) {
					ReentrantLock lock = SessionLock.getInstance().get("dzfsso" + userid);	//注意，lock的id，与getSessionByRedis 调用的不一样
					try {
						lock.lock();
						sessionvo = getSessionByRedis(jedis, userid);

					} finally {
						if (lock != null)
							lock.unlock();
					}
				}

				return sessionvo;
			}
		});
		return sessionvo;
	}

	private SessionCache() {

	}

	public static SessionCache getInstance() {

		return sc;
	}
}
