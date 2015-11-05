package com.dzf.spring;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
}
