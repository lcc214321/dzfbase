package com.dzf.pub;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.pub.cache.CorpCache;
/**
 * @author   
 * 
 */
public class DZFRequestFilter implements Filter {
	//
    private static final ThreadLocal<ServletRequest> tlCurrentRequest = new ThreadLocal<ServletRequest>();

    public static ThreadLocal<ServletRequest> getTlCurrentRequest() {
        return tlCurrentRequest;
    }  

	private FilterConfig filterConfig;

	public DZFRequestFilter() {
		super();
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws 
	IOException, ServletException {
			if(request != null)
		   	request.setCharacterEncoding("UTF-8");
		   	response.setContentType("text/html;charset=UTF-8");
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession session = getSession(request, false);
			
			if(session == null){
				session = req.getSession();
			}
			String url = req.getRequestURI();
			if( req!=null && (url.endsWith(".css") || url.endsWith(".js")  || url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".gif"))){
				filterChain.doFilter(request, response);
	        	return;
	        }
		    if(session!=null){
		    	String userid = (String)session.getAttribute(IGlobalConstants.login_user);
		    	String corp=(String) session.getAttribute(IGlobalConstants.login_corp);
		    	
		    	CorpVO corpVo =null;
		    	if(StringUtil.isEmptyWithTrim(corp)==false)
		    		corpVo=CorpCache.getInstance().get(userid,corp ) ;//(CorpVO)session.getAttribute(IGlobalConstants.login_corp);
		    	if(userid == null && !url.endsWith("/sys/sm_user!login.action") && (corpVo == null && !url.endsWith("/gs_select.jsp") && !url.endsWith("/sys/sm_user!gsSelect.action") && !url.endsWith("/sys/sm_user!gsQuery.action"))){
		    		req.getRequestDispatcher("/login.jsp").forward(req,res);
   				 	return;
		    	}
		    }
			
	        filterChain.doFilter(request, response);
			return;
	}
	
	 public static HttpSession getSession(ServletRequest request, boolean create)
	  {
	    if ((request instanceof HttpServletRequest)) {
	      HttpServletRequest req = (HttpServletRequest)request;
	      HttpSession session = req.getSession(create);
	      return session;
	    }
	    return null;
	  }
	

	public void destroy() {
		this.filterConfig = null;
	}
}