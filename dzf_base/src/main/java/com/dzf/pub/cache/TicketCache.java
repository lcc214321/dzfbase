package com.dzf.pub.cache;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.dzf.framework.comn.IOUtils;
import com.dzf.model.pub.DZFSessionVO;
import com.dzf.pub.SessionRedis.IRedisSessionCallback;
import com.dzf.pub.SessionRedis.SessionRedisClient;

public class TicketCache {
	
	private static TicketCache sc = new TicketCache();


	private Logger log = Logger.getLogger(this.getClass());

	private DZFSessionVO getTicketObjByRedis(Jedis jedis, final String ticket) {
		DZFSessionVO sessionvo = null;
		try {
			byte[] bs = jedis.get(ticket.getBytes());

			if (bs == null) {
				return null;
			}
			sessionvo = (DZFSessionVO) IOUtils.getObject(bs, new TicketSerializable());
			
		} catch (Exception e) {
			log.error("从缓存服务器获取数据出错！",e);
			return null;
		}
		return sessionvo;
	}


	public boolean put(final String ticket, final DZFSessionVO m) {
		boolean bReturn =(Boolean) SessionRedisClient.getInstance().exec(new IRedisSessionCallback() {
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
					
					jedis.set(ticket.getBytes(), IOUtils.getBytes(m, new TicketSerializable()));
					jedis.expire(ticket, 120);		//失效时间, 120秒，原来改过20秒，还是超时 最初是10秒，20161206修改
					return true;
				} catch (Exception e) {
					log.error("从缓存服务器获取数据出错！", e);
				} finally {
					if (lock != null)
						lock.unlock();
				}
				return false;
			}
		});
		return bReturn;
	}



	public DZFSessionVO get(final String ticket) {
		if (ticket == null) {
			return null;
		}
		DZFSessionVO sessionvo = (DZFSessionVO) SessionRedisClient.getInstance().exec(new IRedisSessionCallback() {

			@Override
			public Object exec(Jedis jedis) {
				if (jedis == null)
				{
					return null;
				}
				DZFSessionVO sessionvo = getTicketObjByRedis(jedis, ticket);
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
