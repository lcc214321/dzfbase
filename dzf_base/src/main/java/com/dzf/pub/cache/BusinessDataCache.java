package com.dzf.pub.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.dzf.framework.comn.IOUtils;
import com.dzf.model.pub.DZFSessionVO;
import com.dzf.pub.Redis.IRedisCallback;
import com.dzf.pub.Redis.RedisClient;




public class BusinessDataCache {
	
	private static BusinessDataCache bc = new BusinessDataCache();


	private Logger log = Logger.getLogger(this.getClass());

	
	/**
	 *  大业务对象缓存至redis
	 * @param id 	业务对象全局唯一id
	 * @param obj	(业务对象)
	 * @return	true:成功  false: 失败
	 */
	public boolean put(final String id, final Object obj) {
		
		if (RedisClient.getInstance().getEnabled() == false)	//未启用redis时，无法建立缓存
		{
			return false;
		}
		boolean bReturn =(Boolean) RedisClient.getInstance().exec(new IRedisCallback() {
			
			@Override
			public Object exec(Jedis jedis) {
				
				if (jedis == null)	//没启用缓存服务器，不能执行put
				{
					return false;
				}
				ReentrantLock lock = BusinessDataLock.getInstance().get(id);
				try {
					//加锁
					lock.lock();
					
					jedis.setex(id.getBytes(), 120, toByteArray(obj));
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

	/**
	 * 
	 * @param id 业务对象全局唯一id
	 * @param second 业务对象缓存时间(秒)
	 * @param obj (业务对象)
	 * @return	true:成功  false: 失败
	 */
	public boolean put(final String id, final int second, final Object obj) {
		
		if (RedisClient.getInstance().getEnabled() == false)	//未启用redis时，无法建立缓存
		{
			return false;
		}
		boolean bReturn =(Boolean) RedisClient.getInstance().exec(new IRedisCallback() {
			
			@Override
			public Object exec(Jedis jedis) {
				
				if (jedis == null)	//没启用缓存服务器，不能执行put
				{
					return false;
				}
				ReentrantLock lock = BusinessDataLock.getInstance().get(id);
				try {
					//加锁
					lock.lock();
					
					jedis.setex(id.getBytes(), second, toByteArray(obj));
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
	/**
	 *  读取业务数据缓存，不缓存，返回null
	 * @param id	业务对象全局唯一id
	 * @return	业务对象(无缓存返回null）
	 */
	public Object get(final String id) {
		if (id == null 	|| RedisClient.getInstance().getEnabled() == false)	//id为空，或未启用redis时，无法读取缓存
		{
			return null;
		}
		Object object = RedisClient.getInstance().exec(new IRedisCallback() {

			@Override
			public Object exec(Jedis jedis) {
				if (jedis == null)
				{
					return null;
				}
				Object newobject = getObjectByRedis(jedis, id);
				if (newobject == null) {
					ReentrantLock lock = TicketLock.getInstance().get(id);
					try {
						lock.lock();
						newobject = getObjectByRedis(jedis, id);

					} finally {
						if (lock != null)
							lock.unlock();
					}
				}

				return newobject;
			}
		});
		return object;
	}
	private Object getObjectByRedis(Jedis jedis, final String id) {
		Object obj = null;
		try {
			byte[] bs = jedis.get(id.getBytes());

			if (bs == null) {
				return null;
			}
			obj = toObject(bs);
			
		} catch (Exception e) {
			log.error("从缓存服务器读取业务数据缓存出错:" + e.getMessage(), e);
			return null;
		}
		return obj;
	}
	
	private BusinessDataCache() {

	}

	public static BusinessDataCache getInstance() {

		return bc;
	}
	/**
	 * 对象转byte数组
	 * 
	 * @param obj
	 * @return byte[]
	 */
	private byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bos.flush();
			bytes = bos.toByteArray();

		} catch (IOException ex) {
			log.error("处理redis业务数据缓存过程，对象转byte[]出错：" + ex.getLocalizedMessage(), ex);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception e) {
				}

			}
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
				}
			}
		}
		return bytes;
	}

	/**
	 * 数组转对象
	 * 
	 * @param bytes
	 * @return Object
	 */
	private Object toObject(byte[] bytes) {
		Object obj = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();

		} catch (IOException ex) {
			log.error("处理redis业务数据缓存过程，byte[]转对象出错：" + ex.getLocalizedMessage(), ex);
		} catch (ClassNotFoundException ex) {
			log.error("处理redis业务数据缓存过程，byte[]转对象出错：" + ex.getLocalizedMessage(), ex);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e) {
				}

			}
			if (ois != null) {
				try {
					ois.close();
				} catch (Exception e) {
				}
			}
		}
		return obj;
	}
}
