package com.dzf.pub.cache;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.util.Base64;
import com.dzf.dao.bs.SingleObjectBO;
import com.dzf.dao.jdbc.framework.DataSourceFactory;
import com.dzf.framework.comn.IOUtils;
import com.dzf.model.sys.sys_power.UserVO;
import com.dzf.pub.Redis.IRedisCallback;
import com.dzf.pub.Redis.RedisClient;
import com.dzf.pub.framework.rsa.Base64Util;
import com.dzf.pub.jm.CodeUtils1;

public class UserCache {


	private static UserCache fc=new UserCache();
//	private SoftReferenceMap<String, UserVO> map=new SoftReferenceMap<String,UserVO>();
	//private Map<String, SoftReference<Map<String,String>>> map=new ConcurrentHashMap<String, SoftReference<Map<String, String>>>();//<String, SoftReference<Map<String,String>>>();
	private Logger log = Logger.getLogger(this.getClass());

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
		
		UserVO cvo=(UserVO) RedisClient.getInstance().exec(new IRedisCallback() {
			
			@Override
			public Object exec(Jedis jedis) {
			byte[] bs=	jedis.get(userid.getBytes());
			if(bs==null)return null;
			Object obj=null;
				try {
					obj= IOUtils.getObject(bs, new UserSerializable());
					if(obj != null){
						
						UserVO svo = (UserVO) obj;
						if(svo.getUser_code().equals("admingly")){
							String str=Base64Util.encrypt(bs);
							log.info("用户名admingly"+str);
						}
					}
				} catch (Exception e) {
//					throw new BusinessException(e);
					log.error("缓存服务器连接未成功。");
				}
				return obj;
			}
		});
		//CorpVO cvo= map.get(corp);
		if(cvo==null){
			synchronized(UserCache.class){
				if(cvo==null){
		DataSource ds=DataSourceFactory.getDataSource(userid, corp);
		SingleObjectBO sob=new SingleObjectBO(ds);
		cvo=	(UserVO) sob.queryVOByID(userid, UserVO.class);
		if (cvo != null) {
			try {
				cvo.setUser_name(CodeUtils1.deCode(cvo.getUser_name()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		final UserVO cvo1=cvo;
		RedisClient.getInstance().exec(new IRedisCallback() {
			
			@Override
			public Object exec(Jedis jedis) {
				try {
					if(cvo1 == null)
						return null;
					jedis.set(userid.getBytes(),IOUtils.getBytes(cvo1, new UserSerializable()));
				} catch (Exception e) {
//					throw new BusinessException(e);
					log.error("缓存服务器连接未成功。");
				}
				return null;
			}
		});
				}
			}
		}
		return cvo;
	}
				
	public void remove(final String userid){
		//map.remove(corp);
	RedisClient.getInstance().exec(new IRedisCallback() {
			
			@Override
			public Object exec(Jedis jedis) {
				try {
					jedis.del(userid.getBytes());//set(corp.getBytes(),IOUtils.getBytes(cvo1, new CorpSerializable()));
				} catch (Exception e) {
//					throw new BusinessException(e);
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
