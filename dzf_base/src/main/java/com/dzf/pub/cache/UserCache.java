package com.dzf.pub.cache;

import javax.sql.DataSource;

import com.dzf.dao.bs.SingleObjectBO;
import com.dzf.dao.jdbc.framework.DataSourceFactory;
import com.dzf.model.sys.sys_power.UserVO;
import com.dzf.pub.Field.SoftReferenceMap;

public class UserCache {


	private static UserCache fc=new UserCache();
	private SoftReferenceMap<String, UserVO> map=new SoftReferenceMap<String,UserVO>();
	//private Map<String, SoftReference<Map<String,String>>> map=new ConcurrentHashMap<String, SoftReference<Map<String, String>>>();//<String, SoftReference<Map<String,String>>>();


	public void add(String key,UserVO m){

		map.put(key, m);
	}
	public UserVO get(String userid,String corp){
		UserVO cvo= map.get(userid);
		if(cvo==null){
			synchronized(UserCache.class){
				if(cvo==null){
		DataSource ds=DataSourceFactory.getDataSource(userid, corp);
		SingleObjectBO sob=new SingleObjectBO(ds);
		cvo=	(UserVO) sob.queryVOByID(userid, UserVO.class);
		map.put(userid, cvo);
				}
			}
		}
		return cvo;
	}

		private UserCache() {

		}
	public static UserCache getInstance(){
		return fc;
	}
}
