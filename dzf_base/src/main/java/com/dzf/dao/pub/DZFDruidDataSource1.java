package com.dzf.dao.pub;

import com.atomikos.jdbc.AtomikosDataSourceBean;



public class DZFDruidDataSource1 extends AtomikosDataSourceBean {
private String dataSourceName;

public String getDataSourceName() {
	return dataSourceName;
}

public void setDataSourceName(String dataSourceName) {
	this.dataSourceName = dataSourceName;
	this.setUniqueResourceName(dataSourceName);
}
}
