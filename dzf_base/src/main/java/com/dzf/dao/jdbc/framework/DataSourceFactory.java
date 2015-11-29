package com.dzf.dao.jdbc.framework;

import javax.sql.DataSource;

import com.dzf.spring.SpringUtils;

public class DataSourceFactory {
public static DataSource getDataSource(String user,String corp){
//    BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
//    
//    return (DataSource)factory.getBean("datasource");
    DataSource ds = null;
    try{
    	ds = (DataSource) SpringUtils.getBean("dataSource");
    }catch(Exception e){
    	//e.printStackTrace();
    }
    if(ds == null){
    	ds  = (DataSource) SpringUtils.getBean2("dataSource");
    }
    
    return ds;
}
}
