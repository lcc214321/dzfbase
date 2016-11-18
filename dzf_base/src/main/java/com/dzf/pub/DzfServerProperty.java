package com.dzf.pub;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DzfServerProperty {

	private static DzfServerProperty instance = null;
	
	private String [] saReturn = null;
	public DzfServerProperty() {
		// TODO Auto-generated constructor stub
	}
	public static DzfServerProperty getInstance()
	{
		if (instance == null)
		{
			instance = new DzfServerProperty();
			instance.readDzfServerCfg();
		}
		return instance;
	}
	private void readDzfServerCfg()
	{
		

		 Properties prop = new Properties();   
		 InputStream in = null;
		 try{
			 //读取属性文件ssoserver.properties
			 in =  this.getClass().getResourceAsStream("/dzfserver.properties");

			 prop.load(in);     ///加载属性列表
			 saReturn = new String[5];
			 saReturn[0] = prop.getProperty("ssoserver").trim();
			 if (saReturn[0].endsWith("/") == false)
			 {
				 saReturn[0] += "/";
			 }
			 saReturn[1] = prop.getProperty("appid");
			 saReturn[2] = prop.getProperty("loginjsp");
			 saReturn[3] = prop.getProperty("usessoserver");
			 saReturn[4] = prop.getProperty("forbiddenssoaddress");
		 }   catch  (IOException e1)  {    

			 //filter写不写日志？

		 }
		 finally {
			 if (in != null)
			 {
				 try {
					 in.close();
				 }
				 catch (Exception ex)
				 {}
			 }
		 }
	}
	/**
	 * String[] ssoserver, appid, loginjsp
	 * @return
	 */
	public String[] getDzfServerCfg()
	{
		return saReturn;
	}
}
