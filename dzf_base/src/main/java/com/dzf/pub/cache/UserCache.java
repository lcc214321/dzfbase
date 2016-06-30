package com.dzf.pub.cache;

import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.dzf.dao.bs.SingleObjectBO;
import com.dzf.dao.jdbc.framework.DataSourceFactory;
import com.dzf.framework.comn.IOUtils;
import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.model.sys.sys_power.UserVO;
import com.dzf.pub.Redis.IRedisCallback;
import com.dzf.pub.Redis.RedisClient;
import com.dzf.pub.framework.rsa.Base64Util;
import com.dzf.pub.jm.CodeUtils1;

import redis.clients.jedis.Jedis;

public class UserCache {


	private static UserCache fc=new UserCache();
//	private SoftReferenceMap<String, UserVO> map=new SoftReferenceMap<String,UserVO>();
	//private Map<String, SoftReference<Map<String,String>>> map=new ConcurrentHashMap<String, SoftReference<Map<String, String>>>();//<String, SoftReference<Map<String,String>>>();
	private Logger log = Logger.getLogger(this.getClass());
	private UserVO getUserVOByRedis(Jedis jedis, final String userid,final String corp){
		UserVO obj=null;
		try {
	byte[] bs=	jedis.get(userid.getBytes());


			if(bs == null){
				return null;
			}
			obj= (UserVO) IOUtils.getObject(bs, new UserSerializable());
			if(obj!=null&&obj.getUser_code().equals("admingly")){
				String str=Base64Util.encrypt(bs);
				log.info("用户名admingly"+str);
			}
		} catch (Exception e) {
			log.error("缓存服务器连接未成功。");
			return null;
		}
		return obj;
	}
	private UserVO getUserVO(final String userid,final String corp){
		DataSource ds=DataSourceFactory.getDataSource(userid, corp);
		SingleObjectBO sob=new SingleObjectBO(ds);
		UserVO	cvo=	(UserVO) sob.queryVOByID(userid, UserVO.class);
		if (cvo != null) {
			if (cvo != null) {
				try {
					cvo.setUser_name(CodeUtils1.deCode(cvo.getUser_name()));
				} catch (Exception e) {
					log.error("解密用户名失败！cvo.getUser_name()为"+cvo.getUser_name()+"，不能解密",e);
				}
			}
		}
		return cvo;
	}
	public void add(final String key,final UserVO m){

		RedisClient.getInstance().exec(new IRedisCallback() {
			
			@Override
			public Object exec(Jedis jedis) {
				try {
					jedis.set(key.getBytes(),IOUtils.getBytes(m, new UserSerializable()));
				} catch (Exception e) {
					log.error("缓存服务器连接未成功。");
				}
				return null;
			}
		});
	}
	public UserVO get(final String userid,final String corp){
		if(userid == null){
			return null;
		}
		UserVO cvo=(UserVO) RedisClient.getInstance().exec(new IRedisCallback() {
			
			@Override
			public Object exec(Jedis jedis) {
				UserVO cvo=null;
				if(jedis==null){
					cvo=getUserVO(userid,corp);
					return cvo;
				}
				cvo=getUserVOByRedis(jedis,userid,corp);
					if(cvo==null){
						ReentrantLock lock=null;
				
						UserLock.getInstance().get(userid);//	LockUtils.getInstance().getNextID(corp);
						lock.lock();
					
						try{
						
						cvo= getUserVOByRedis(jedis,userid,corp);
						if(cvo==null){
					cvo=getUserVO(userid,corp);
					if(cvo == null)	return null;
					try {
						
						jedis.set(userid.getBytes(),IOUtils.getBytes(cvo, new UserSerializable()));
					} catch (Exception e) {
						log.error("缓存服务器连接未成功。");
					}

						}
				}finally{
					if(lock!=null)
					lock.unlock();
				}
					}
				
			
			return cvo;
		}
			 });
			 return cvo;
	}
//	public UserVO get(final String userid,final String corp){
//		
//		UserVO cvo=(UserVO) RedisClient.getInstance().exec(new IRedisCallback() {
//			
//			@Override
//			public Object exec(Jedis jedis) {
//			byte[] bs=	jedis.get(userid.getBytes());
//			if(bs==null)return null;
//			Object obj=null;
//				try {
//					obj= IOUtils.getObject(bs, new UserSerializable());
//					if(obj != null){
//						
//						UserVO svo = (UserVO) obj;
//						if(svo.getUser_code().equals("admingly")){
//							String str=Base64Util.encrypt(bs);
//							log.info("用户名admingly"+str);
//						}
//					}
//				} catch (Exception e) {
//
//					log.error("缓存服务器连接未成功。");
//				}
//				return obj;
//			}
//		});
//		//CorpVO cvo= map.get(corp);
//		if(cvo==null){
//			synchronized(UserCache.class){
//				if(cvo==null){
//		DataSource ds=DataSourceFactory.getDataSource(userid, corp);
//		SingleObjectBO sob=new SingleObjectBO(ds);
//		cvo=	(UserVO) sob.queryVOByID(userid, UserVO.class);
//		if (cvo != null) {
//			try {
//				cvo.setUser_name(CodeUtils1.deCode(cvo.getUser_name()));
//			} catch (Exception e) {
//				log.error("解密用户名失败！cvo.getUser_name()为"+cvo.getUser_name()+"，不能解密",e);
//			}
//		}
//		final UserVO cvo1=cvo;
//		RedisClient.getInstance().exec(new IRedisCallback() {
//			
//			@Override
//			public Object exec(Jedis jedis) {
//				try {
//					if(cvo1 == null)
//						return null;
//					jedis.set(userid.getBytes(),IOUtils.getBytes(cvo1, new UserSerializable()));
//				} catch (Exception e) {
//
//					log.error("缓存服务器连接未成功。");
//				}
//				return null;
//			}
//		});
//				}
//			}
//		}
//		return cvo;
//	}
				
	public void remove(final String userid){
		//map.remove(corp);
	RedisClient.getInstance().exec(new IRedisCallback() {
			
			@Override
			public Object exec(Jedis jedis) {
				try {
					jedis.del(userid.getBytes());//set(corp.getBytes(),IOUtils.getBytes(cvo1, new CorpSerializable()));
				} catch (Exception e) {

					log.error("缓存服务器连接未成功。");
				}
				return null;
			}
		});
	}

	
	public String getUserName(String userid,String corp){
		if(userid == null || userid.trim().length() == 0){
			return "";
		}
		UserVO cvo= get(userid,corp);
		return cvo == null ? "" : cvo.getUser_name();
	}

		private UserCache() {

		}
	public static UserCache getInstance(){
		return fc;
	}
}
