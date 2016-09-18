package com.dzf.spring;

import com.dzf.pub.cache.ServletRequestCache;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringUtils {
	public static Object getBean(String beanname) {
		Object obj = null;
		try {
			ApplicationContext ac = WebApplicationContextUtils
					.getWebApplicationContext(ServletActionContext.getServletContext());

			obj = ac.getBean(beanname);
		} catch (Exception e) {

		}

		if (obj == null) {
			obj = SpringContextHolder.getBean(beanname);
		}
		return obj;
	}

	public static Object getBean2(String beanName) {
		HttpServletRequest request = (HttpServletRequest) ServletRequestCache.getInstance().getThreadLocal().get();
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		return ctx.getBean(beanName);
	}
}