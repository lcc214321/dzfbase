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

public class TicketCache {
	
	private static TicketCache sc = new TicketCache();


	private Logger log = Logger.getLogger(this.getClass());

	private DZFSession getTicketObjByRedis(Jedis jedis, final String ticket) {
		DZFSession obj = null;
		try {
			byte[] bs = jedis.get(ticket.getBytes());

			if (bs == null) {
				return null;
			}
			DZFSessionVO sessionvo = (DZFSessionVO) IOUtils.getObject(bs, new TicketSerializable());
			if (sessionvo != null)
			{
				obj = sessionvo.getSessions()[0];
			}
		} catch (Exception e) {
			log.error("缓存服务器连接未成功。");
			return null;
		}
		return obj;
	}


	public boolean put(final String ticket, final DZFSession m) {
		boolean bReturn =(Boolean) RedisClient.getInstance().exec(new IRedisCallback() {
			@Override
			public Object exec(Jedis jedis) {
				
				if (jedis == null)	//没启用缓存服务器，不能执行put
				{
					return false;
				}
				ReentrantLock lock = TicketLock.getInstance().get(ticket);
				try {
					//加锁
					lock.lock();
					
					DZFSessionVO sessionvo = new DZFSessionVO();
					sessionvo.setSessions(new DZFSession[] {m});
					
					jedis.set(ticket.getBytes(), IOUtils.getBytes(sessionvo, new TicketSerializable()));
					jedis.expire(ticket, 10);		//失效时间, 10秒
					return true;
				} catch (Exception e) {
					log.error("缓存服务器连接未成功。", e);
				} finally {
					if (lock != null)
						lock.unlock();
				}
				return false;
			}
		});
		return bReturn;
	}



	public DZFSession get(final String ticket) {
		if (ticket == null) {
			return null;
		}
		DZFSession sessionvo = (DZFSession) RedisClient.getInstance().exec(new IRedisCallback() {

			@Override
			public Object exec(Jedis jedis) {
				if (jedis == null)
				{
					return null;
				}
				DZFSession sessionvo = getTicketObjByRedis(jedis, ticket);
				if (sessionvo == null) {
					ReentrantLock lock = TicketLock.getInstance().get(ticket);	//注意，lock的id，与getSessionByRedis 调用的不一样
					try {
						lock.lock();
						sessionvo = getTicketObjByRedis(jedis, ticket);

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

	private TicketCache() {

	}

	public static TicketCache getInstance() {

		return sc;
	}
}
