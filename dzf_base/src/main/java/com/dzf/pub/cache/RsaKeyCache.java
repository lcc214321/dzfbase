package com.dzf.pub.cache;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.dzf.framework.comn.IOUtils;
import com.dzf.model.pub.RsaKeyVO;
import com.dzf.pub.SessionRedis.IRedisSessionCallback;
import com.dzf.pub.SessionRedis.SessionRedisClient;
import com.dzf.pub.framework.rsa.RSACoder;
import com.dzf.pub.util.RSAUtils;

public class RsaKeyCache {
	private static RsaKeyCache sc = new RsaKeyCache();

	private String defaultPublicKey = null;
	private String defaultPrivateKey = null;
	
	private Boolean isInit = Boolean.FALSE;
	
	private Logger log = Logger.getLogger(this.getClass());

	private final String key = "SSODzfRsaKey";

	private RsaKeyVO getRsaKeysByRedis(Jedis jedis) {
		RsaKeyVO keyvo = null;
		try {
			byte[] bs = jedis.get(key.getBytes());

			if (bs == null) {
				return null;
			}
			keyvo = (RsaKeyVO) IOUtils.getObject(bs, new RsaKeySerializable());
		} catch (Exception e) {
			log.error("缓存服务器连接未成功。");
			return null;
		}
		return keyvo;
	}
/**
 * 
 * @param publicKey
 * @param privateKey
 */
	private void putKeyVO(final RsaKeyVO keyvo) {

		SessionRedisClient.getInstance().exec(new IRedisSessionCallback() {
			@Override
			public Object exec(Jedis jedis) {
				try {
					
					//！！！！暂时删除，调试用！！！！！！！！！！！！
//					if (jedis.exists(key.getBytes()))
//					{
//						jedis.del(key.getBytes());
//					}
					//*****^^^^^^^^^^**************
					
					//setnx : 当且仅当 key 不存在 时设置
					jedis.setnx(key.getBytes(), IOUtils.getBytes(keyvo, new RsaKeySerializable()));
				} catch (Exception e) {
					log.error("缓存服务器连接未成功。");
				}
				return null;
			}
		});
	}

//	public void remove() {
//
//		SessionRedisClient.getInstance().exec(new IRedisSessionCallback() {
//
//			@Override
//			public Object exec(Jedis jedis) {
//				try {
//					jedis.del(key.getBytes());
//
//				} catch (Exception e) {
//
//					log.error("缓存服务器连接未成功。");
//					return null;
//				}
//				return null;
//			}
//		});
//	}

	public RsaKeyVO getRsaKeyVO() {

		RsaKeyVO  keyvo = (RsaKeyVO) SessionRedisClient.getInstance().exec(new IRedisSessionCallback() {

			@Override
			public Object exec(Jedis jedis) {
				RsaKeyVO keyvo = getRsaKeysByRedis(jedis);
				if (keyvo == null) {
					ReentrantLock lock = RsaKeyLock.getInstance().get(key);
					lock.lock();
					try {
						keyvo = getRsaKeysByRedis(jedis);

					} finally {
						lock.unlock();
					}
				}

				return keyvo;
			}
		});
		return keyvo;
	}
	public String getPublicKey()
	{
		RsaKeyVO keyvo = getRsaKeyVO();
		return (keyvo == null ? defaultPublicKey : keyvo.getPublicKey());
	}
	public String getPrivateKey()
	{
		RsaKeyVO keyvo = getRsaKeyVO();
		return (keyvo == null ? defaultPrivateKey : keyvo.getPrivateKey());
	}
	private RsaKeyCache() {

	}

	private synchronized RsaKeyVO initKey() {
	
		if (sc.isInit)
		{
			return null;
		}
		RsaKeyVO keyvo = null;
		try {
 
			keyvo = new RsaKeyVO();
			
//			Map<String, Object> keyMap = RSACoder.initKey(); 
//			keyvo.setPublicKey(RSACoder.getPublicKey(keyMap));
//			keyvo.setPrivateKey(RSACoder.getPrivateKey(keyMap));
			
			RSAPublicKey publickey = RSAUtils.getDefaultPublicKey();
			defaultPublicKey = RSACoder.encryptBASE64(publickey.getEncoded());
			
			RSAPrivateKey privatekey = RSAUtils.getDefaultPrivateKey();
			defaultPrivateKey = RSACoder.encryptBASE64(privatekey.getEncoded());
			
			keyvo.setPublicKey(defaultPublicKey);
			keyvo.setPrivateKey(defaultPrivateKey);
			
			putKeyVO(keyvo);
		}
		catch (Exception e)
		{
			log.error(e);
		}
		sc.isInit = Boolean.TRUE;
		return keyvo;
	}

	public static RsaKeyCache getInstance() {
		if (sc.isInit == false)
		{
			sc.initKey();
		}
		return sc;
	}
}
