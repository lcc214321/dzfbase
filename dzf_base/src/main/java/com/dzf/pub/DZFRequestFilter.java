package com.dzf.pub;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dzf.dao.bs.SingleObjectBO;
import com.dzf.dao.jdbc.framework.SQLParameter;
import com.dzf.dao.jdbc.framework.processor.ColumnProcessor;
import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.pub.cache.CorpCache;
import com.dzf.pub.cache.ServletRequestCache;
import com.dzf.pub.framework.rsa.RSACoderUtils;
import com.dzf.pub.util.RSAUtils;
/**
 * @author   
 * 
 */
public class DZFRequestFilter implements Filter {
	//
//    private static final ThreadLocal<ServletRequest> tlCurrentRequest = new ThreadLocal<ServletRequest>();
//
//    public static ThreadLocal<ServletRequest> getTlCurrentRequest() {
//        return tlCurrentRequest;
//    }  

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
			boolean needClearTl = false;
			try {
				ServletRequestCache.getInstance().getThreadLocal().set(request);
	    //			getTlCurrentRequest().set(request);
//	    			needClearTl = true;
			} finally {
				if(needClearTl) {				
					try {
						ServletRequestCache.getInstance().getThreadLocal().remove();
//						getTlCurrentRequest().remove();
					} catch (Exception e) {
					}
				}
			}
		
			if(request != null)
		   	request.setCharacterEncoding("UTF-8");
		   	response.setContentType("text/html;charset=UTF-8");
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession session = getSession(request, false);
			
			if(session == null){
				session = req.getSession();
				
				RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
				
				String modulus=new String(Hex.encodeHex(publicKey.getModulus().toByteArray()));
				String exponent=new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray()));
				session.setAttribute("MODULUS",modulus);
				session.setAttribute("EXPONENT",exponent);
			}
			String url = req.getRequestURI();
			if( req!=null && (url.endsWith("DzfApplet.jar")||url.endsWith("fileupload.jar") || url.endsWith(".css") || url.endsWith(".js")  || url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".gif"))){
				filterChain.doFilter(request, response);
	        	return;
	        }
			//会计公司注册.....zpm增加
			if(url.endsWith("/wbx/invoice!inputExpBill.action") || url.endsWith("/404.html") || url.endsWith("/500.html") || url.endsWith("/au/image.jsp") || url.endsWith("/do/dz_registered.jsp") || url.endsWith("/do/forgot_password.jsp") || url.endsWith("/do/results.jsp")
					|| url.endsWith("register_act!sendMessage.action") || url.endsWith("register_act!saveRegistered.action") ){
				filterChain.doFilter(request, response);
	        	return;
			}
			//小巨人同步
			if(url.endsWith("/sys/xjr_sync!sync.action")){
				filterChain.doFilter(request, response);
	        	return;
			}
			//会计工厂登录
			if(url.endsWith("/loginMac.jsp")){
				filterChain.doFilter(request, response);
	        	return;
			}
		    if(session!=null){
		    	String userid = (String)session.getAttribute(IGlobalConstants.login_user);
		    	String corp=(String) session.getAttribute(IGlobalConstants.login_corp);
		    	
		    	CorpVO corpVo =null;
/*		    	if(StringUtil.isEmptyWithTrim(corp)==false)
		    		corpVo=CorpCache.getInstance().get(userid,corp ) ;//(CorpVO)session.getAttribute(IGlobalConstants.login_corp);
*/		    	
		    	
		    	if(! url.endsWith("sm_user!logout.action") &&  ! url.endsWith("register_act!saveRegistered.action") && !url.endsWith("sm_user!updatePwdLogin.action") && ! url.endsWith("forgot_password.jsp") && ! url.endsWith("dz_registered.jsp") && (userid == null || (corp == null && !url.endsWith("/gs_select.jsp") && !url.endsWith("/sys/sm_user!gsSelect.action") && !url.endsWith("/sys/sm_user!gsQuery.action"))) && !url.endsWith("/sys/sm_user!login.action") && !url.endsWith("/sys/sm_user!getLogin.action") && !url.endsWith("/sys/sm_user!login2.action") && !url.endsWith("/sys/sm_user!dzfLogin.action") && !url.endsWith("searchPsw.jsp") && !url.endsWith("/searchPsw!getPswBack.action") && !url.endsWith("/searchPsw!sandYZcode.action")){
			    	if(url.endsWith(".action")){
			    		String message = (String)session.getAttribute(IGlobalConstants.logout_msg);
			    		if(message == null) message = "请先登陆!";
		    			String json = "{\"msg\": \"" + message + "\",\"rows\": [],\"success\": false,\"total\": 0}";
		    			response.setContentType("text/html;charset=utf-8");
		    			response.getWriter().write(json);
//			    		session.setAttribute("errorMsg", message);
//						req.getRequestDispatcher("/error_kj.jsp").forward(req,res);
	   				 	return;
			    	}
		    		req.getRequestDispatcher("/login.jsp").forward(req,res);
   				 	return;
		    	}
/*boolean b=false;
try{
	b = RSACoderUtils.validateToken(session);
}catch(Exception e){
	session.setAttribute("errorMsg", "无权操作,请联系管理员!");
//	req.getRequestDispatcher("/error_kj.jsp").forward(req,res);
	 	return;
}*/
//				String path =req.getServletPath();
//		    	if(userid!=null&&corpVo!=null&&!path.endsWith("/index.jsp")){
//		    		if(!checkPageAuth( (HttpServletRequest)request,userid,corpVo.getPk_corp())){
//		    			request.getRequestDispatcher(errorPage).forward(request, response);//璺宠浆鍒颁俊鎭彁绀洪〉闈紒锛�
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
