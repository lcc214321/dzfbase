package com.dzf.pub.cache;

import javax.sql.DataSource;

import com.dzf.dao.bs.SingleObjectBO;
import com.dzf.dao.jdbc.framework.DataSourceFactory;
import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.pub.Field.SoftReferenceMap;

public class CorpCache {
private static CorpCache fc=new CorpCache();
private SoftReferenceMap<String, CorpVO> map=new SoftReferenceMap<String,CorpVO>();
//private Map<String, SoftReference<Map<String,String>>> map=new ConcurrentHashMap<String, SoftReference<Map<String, String>>>();//<String, SoftReference<Map<String,String>>>();


public void add(String key,CorpVO m){

	map.put(key, m);
}
public CorpVO get(String userid,String corp){
	CorpVO cvo= map.get(corp);
	if(cvo==null){
		synchronized(CorpCache.class){
			if(cvo==null){
	DataSource ds=DataSourceFactory.getDataSource(userid, corp);
	SingleObjectBO sob=new SingleObjectBO(ds);
	cvo=	(CorpVO) sob.queryVOByID(corp, CorpVO.class);
	map.put(corp, cvo);
			}
		}
	}
	return cvo;
}
public CorpVO get(String userid,String corp,boolean isforce){
	if(isforce)
	map.remove(corp);
	CorpVO cvo= map.get(corp);
	if(cvo==null){
		synchronized(CorpCache.class){
			if(cvo==null){
	DataSource ds=DataSourceFactory.getDataSource(userid, corp);
	SingleObjectBO sob=new SingleObjectBO(ds);
	cvo=	(CorpVO) sob.queryVOByID(corp, CorpVO.class);
	map.put(corp, cvo);
			}
		}
	}
	return cvo;
}
	private CorpCache() {

	}
public static CorpCache getInstance(){
	return fc;
}
}
