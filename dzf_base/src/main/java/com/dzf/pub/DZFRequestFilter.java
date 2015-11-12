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

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dzf.dao.bs.SingleObjectBO;
import com.dzf.dao.jdbc.framework.SQLParameter;
import com.dzf.dao.jdbc.framework.processor.ColumnProcessor;
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
	
	private String errorPage;

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
		    	if(userid == null && !url.endsWith("/sys/sm_user!login.action") && !url.endsWith("/sys/sm_user!dzfLogin.action") && (corpVo == null && !url.endsWith("/gs_select.jsp") && !url.endsWith("/sys/sm_user!gsSelect.action") && !url.endsWith("/sys/sm_user!gsQuery.action"))){
		    		req.getRequestDispatcher("/login.jsp").forward(req,res);
   				 	return;
		    	}

//				String path =req.getServletPath();
//		    	if(userid!=null&&corpVo!=null&&!path.endsWith("/index.jsp")){
//		    		if(!checkPageAuth( (HttpServletRequest)request,userid,corpVo.getPk_corp())){
//		    			request.getRequestDispatcher(errorPage).forward(request, response);//跳转到信息提示页面！！ 
//		    		}
//		    	}
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
	
	private Boolean checkPageAuth(HttpServletRequest request,String userid,String pk_corp){
		
		String path = request.getRequestURI();
//		String path = request.getServletPath().substring(0, request.getServletPath().indexOf("!"));
		
		String sql = new String("SELECT 1 FROM SM_POWER_FUNC POWER "+
				" INNER JOIN SM_FUNNODE FUN ON POWER.RESOURCE_DATA_ID=FUN.PK_FUNNODE "+
				" INNER JOIN SM_USER_ROLE ROL ON ROL.PK_ROLE=POWER.PK_ROLE "+
				" WHERE ROL.CUSERID =? AND ROL.PK_CORP=?  AND FILE_DIR = ? AND NVL(POWER.DR,0)=0 AND NVL(ROL.DR,0)=0 ");
		SQLParameter sp = new SQLParameter();
		sp.addParam(userid);
		sp.addParam(pk_corp);
		sp.addParam(path);
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		SingleObjectBO sbo = wc.getBean(SingleObjectBO.class);
		
		String c = (String)sbo.executeQuery(sql, sp, new ColumnProcessor());
		
		return c!=null;
	}
	
}