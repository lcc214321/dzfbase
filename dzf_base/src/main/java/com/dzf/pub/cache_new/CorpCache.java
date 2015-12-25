package com.dzf.pub.cache_new;

import javax.sql.DataSource;

import redis.clients.jedis.Jedis;

import com.dzf.dao.bs.SingleObjectBO;
import com.dzf.dao.jdbc.framework.DataSourceFactory;
import com.dzf.framework.comn.IOUtils;
import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.pub.BusinessException;
import com.dzf.pub.Redis.IRedisCallback;
import com.dzf.pub.Redis.RedisClient;
import com.dzf.pub.cache.CorpSerializable;

public class CorpCache {
private static CorpCache fc=new CorpCache();
//private SoftReferenceMap<String, CorpVO> map=new SoftReferenceMap<String,CorpVO>();


public void add(final String key,final CorpVO m){
RedisClient.getInstance().exec(new IRedisCallback() {
		
		@Override
		public Object exec(Jedis jedis) {
			try {
				jedis.set(key.getBytes(),IOUtils.getBytes(m, new CorpSerializable()));
			} catch (Exception e) {
				
			}
			return null;
		}
	});
}
public CorpVO get(String userid,final String corp){
	
	 CorpVO cvo=(CorpVO) RedisClient.getInstance().exec(new IRedisCallback() {
		
		@Override
		public Object exec(Jedis jedis) {
		byte[] bs=	jedis.get(corp.getBytes());
		Object obj=null;
			try {
				if(bs == null){
					return obj;
				}
				obj= IOUtils.getObject(bs, new CorpSerializable());
			} catch (Exception e) {
				throw new BusinessException(e);
			}
			return obj;
		}
	});
	//CorpVO cvo= map.get(corp);
	if(cvo==null){
		synchronized(CorpCache.class){
			if(cvo==null){
	DataSource ds=DataSourceFactory.getDataSource(userid, corp);
	SingleObjectBO sob=new SingleObjectBO(ds);
	cvo=	(CorpVO) sob.queryVOByID(corp, CorpVO.class);
	final CorpVO cvo1=cvo;
	RedisClient.getInstance().exec(new IRedisCallback() {
		
		@Override
		public Object exec(Jedis jedis) {
			try {
				if(cvo1 == null){
					return null;
				}
				jedis.set(corp.getBytes(),IOUtils.getBytes(cvo1, new CorpSerializable()));
			} catch (Exception e) {
				throw new BusinessException(e);
			}
			return null;
		}
	});
			}
		}
	}
	return cvo;
}
			
public void remove(final String corp){
	//map.remove(corp);
RedisClient.getInstance().exec(new IRedisCallback() {
		
		@Override
		public Object exec(Jedis jedis) {
			try {
				jedis.del(corp.getBytes());//set(corp.getBytes(),IOUtils.getBytes(cvo1, new CorpSerializable()));
			} catch (Exception e) {
				throw new BusinessException(e);
			}
			return null;
		}
	});
}

public CorpVO get(String userid,String corp,boolean isforce){
	return get(userid,corp);
//	if(isforce)
//	map.remove(corp);
//	CorpVO cvo= map.get(corp);
//	if(cvo==null){
//		synchronized(CorpCache.class){
//			if(cvo==null){
//	DataSource ds=DataSourceFactory.getDataSource(userid, corp);
//	SingleObjectBO sob=new SingleObjectBO(ds);
//	cvo=	(CorpVO) sob.queryVOByID(corp, CorpVO.class);
//	map.put(corp, cvo);
//			}
//		}
//	}
//	return cvo;
}
	private CorpCache() {

	}
public static CorpCache getInstance(){
	return fc;
}
}
