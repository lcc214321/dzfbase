package com.dzf.dao.pub;

import com.alibaba.druid.pool.DruidDataSource;

public class DZFDruidDataSource extends DruidDataSource {
private String dataSourceName;

public String getDataSourceName() {
	return dataSourceName;
}

public void setDataSourceName(String dataSourceName) {
	this.dataSourceName = dataSourceName;
}
}
