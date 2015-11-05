package com.dzf.pub;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class SessionUtil {

	public SessionUtil() {
		// TODO Auto-generated constructor stub
	}
	public static HttpSession getSession(){
	return ServletActionContext.getRequest().getSession();
	}
}
