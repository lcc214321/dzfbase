package com.dzf.spring;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dzf.pub.DZFRequestFilter;

public class SpringUtils {

	public SpringUtils() {
		// TODO Auto-generated constructor stub
	}
	public static Object getBean(String beanname){
		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(ServletActionContext
						.getServletContext());
		return ac.getBean(beanname);
	}
	
	 public static Object getBean2(String beanName) {
	    	HttpServletRequest request = (HttpServletRequest)DZFRequestFilter.getTlCurrentRequest().get();
			HttpSession session = request.getSession();
			ServletContext servletContext = session.getServletContext();		
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			return ctx.getBean(beanName);
	    }
}
