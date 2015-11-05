package com.dzf.dao.jdbc.framework;

import javax.sql.DataSource;

import com.dzf.spring.SpringUtils;

public class DataSourceFactory {
public static DataSource getDataSource(String user,String corp){
//    BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
//    
//    return (DataSource)factory.getBean("datasource");
    return (DataSource) SpringUtils.getBean("dataSource");
}
}
