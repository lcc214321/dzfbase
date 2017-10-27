package com.dzf.pub.cache;

import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.dzf.dao.bs.SingleObjectBO;
import com.dzf.dao.jdbc.framework.DataSourceFactory;
import com.dzf.framework.comn.IOUtils;
import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.pub.Redis.IRedisCallback;
import com.dzf.pub.Redis.RedisClient;
import com.dzf.pub.jm.CodeUtils1;

public class CorpCache {
private static CorpCache fc=new CorpCache();
//private SoftReferenceMap<String, CorpVO> map=new SoftReferenceMap<String,CorpVO>();
private Logger log = Logger.getLogger(this.getClass());
private CorpVO getCorpVOByRedis(Jedis jedis, final String userid,final String corp){
	CorpVO obj=null;
	try {
byte[] bs=	jedis.get(corp.getBytes());


		if(bs == null){
			return null;
		}
		obj= (CorpVO) IOUtils.getObject(bs, new CorpSerializable());
	} catch (Exception e) {
		log.error("从缓存服务器获取数据出错！",e);
		return null;
	}
	return obj;
}
public void add(final String key,final CorpVO m){
	RedisClient.getInstance().exec(new IRedisCallback() {
		@Override
		public Object exec(Jedis jedis) {
			if(jedis == null){
				return null;
			}
			try {
				jedis.set(key.getBytes(),IOUtils.getBytes(m, new CorpSerializable()));
			} catch (Exception e) {
				log.error("从缓存服务器获取数据出错！",e);
			}
			return null;
		}
	});
}

private CorpVO getCorpVO(final String userid,final String corp){
	DataSource ds=DataSourceFactory.getDataSource(userid, corp);
	SingleObjectBO sob=new SingleObjectBO(ds);
	CorpVO	cvo=	(CorpVO) sob.queryVOByID(corp, CorpVO.class);
	if (cvo != null) {
		try {
			cvo.setUnitname(CodeUtils1.deCode(cvo.getUnitname()));
			cvo.setUnitshortname(CodeUtils1.deCode(cvo.getUnitshortname()));
			cvo.setPhone1(CodeUtils1.deCode(cvo.getPhone1()));
			cvo.setPhone2(CodeUtils1.deCode(cvo.getPhone2()));
		} catch (Exception e) {
			log.error("解密失败！",e);
		}
	}
	return cvo;
}


public void remove(final String corp){
	//map.remove(corp);
RedisClient.getInstance().exec(new IRedisCallback() {
		
		@Override
		public Object exec(Jedis jedis) {
			if(jedis == null){
				return null;
			}
			try {
				jedis.del(corp.getBytes());//set(corp.getBytes(),IOUtils.getBytes(cvo1, new CorpSerializable()));
			} catch (Exception e) {
//				throw new BusinessException(e);
				log.error("从缓存服务器获取数据出错！",e);
				return null;
			}
			return null;
		}
	});
}
public CorpVO get(final String userid,final String corp){
	if(corp == null){
		return null;
	}
	CorpVO cvo=(CorpVO) RedisClient.getInstance().exec(new IRedisCallback() {
				@Override
				public Object exec(Jedis jedis) {
					if(jedis == null){
						return null;
					}
					CorpVO cvo= null;
//				 	CorpVO cvo= getCorpVOByRedis(jedis,userid,corp);
					ReentrantLock lock=CorpLock.getInstance().get(corp);//	LockUtils.getInstance().getNextID(corp);
					lock.lock();
					try{
						cvo= getCorpVOByRedis(jedis,userid,corp);
						if(cvo==null){
							cvo=getCorpVO(userid,corp);
							if(cvo!=null){
								try {
									jedis.set(corp.getBytes(),IOUtils.getBytes(cvo, new CorpSerializable()));
								} catch (Exception e) {
									log.error("从缓存服务器获取数据出错！",e);
								}
							}
						}
					}finally{
						lock.unlock();
					}
					return cvo;
				}
		});
	 if(cvo == null){
		 cvo =  getCorpVO(userid,corp);
	 }
	 return cvo;
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
