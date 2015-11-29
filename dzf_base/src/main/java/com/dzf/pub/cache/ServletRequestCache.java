package com.dzf.pub.cache;

import javax.servlet.ServletRequest;


public class ServletRequestCache {
private static ServletRequestCache fc=new ServletRequestCache();
private InheritableThreadLocal<ServletRequest> map=new InheritableThreadLocal<ServletRequest>();
public InheritableThreadLocal<ServletRequest> getThreadLocal(){
	
	return map;
}
	private ServletRequestCache() {

	}
public static ServletRequestCache getInstance(){
	return fc;
}

}
