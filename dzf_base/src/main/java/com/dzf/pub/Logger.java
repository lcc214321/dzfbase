package com.dzf.pub;


public class Logger {

	public Logger() {
		// TODO Auto-generated constructor stub
	}
	public static void error(Object o,String s, Throwable t){
		 org.apache.log4j.Logger log1 = org.apache.log4j.Logger.getLogger(o.getClass());
		 log1.error(s, t);
	}
	public static boolean isDebugEnabled(Object o){
		 org.apache.log4j.Logger log1 = org.apache.log4j.Logger.getLogger(o.getClass());
		return log1.isDebugEnabled();
	}
	public static void debug(Object o,String s){
		 org.apache.log4j.Logger log1 = org.apache.log4j.Logger.getLogger(o.getClass());
		log1.debug(s);
	}
	public static void info(Object o,String s){
		 org.apache.log4j.Logger log1 = org.apache.log4j.Logger.getLogger(o.getClass());
		log1.info(s);
	}
}
