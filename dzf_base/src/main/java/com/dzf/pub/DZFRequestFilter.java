package com.dzf.pub;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.dzf.model.pub.DZFSessionVO;
import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.pub.cache.ServletRequestCache;
import com.dzf.pub.cache.SessionCache;
import com.dzf.pub.framework.rsa.RSACoderUtils;
import com.dzf.pub.httpclient.HttpClientUtil;
import com.dzf.pub.session.DZFSession;
import com.dzf.pub.session.DzfCookieTool;
import com.dzf.pub.session.DzfSessionTool;
import com.dzf.pub.util.RSAUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
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
			
			//session修改开始			
			String token = DzfCookieTool.getToken(request);
			
			boolean bDeleteSession = false;
			boolean bDeleteCookie = false;
			String errorRetMsg = null;	//错误返回提示信息
			try 
			{
				if (token == null)
				{
					//可能是登录过程，客户端无cookie
				}
				else
				{
					String realtoken = DzfCookieTool.getRealToken(token);
					if (StringUtil.isEmptyWithTrim(realtoken) == false)
					{
						String[] sa = realtoken.split(",");
						String remoteIp = sa[0];
						String pk_user = sa[1];
						String appid = sa[2];
						
						String sessionUser = (String)session.getAttribute(IGlobalConstants.login_user);
						
						if (StringUtil.isEmptyWithTrim(sessionUser))
						{
							//当客户端token存在，tomcat服务器缓存里面又没有用户session时，才到redis服务器检查是否有在线信息
							DZFSessionVO sessionvo = SessionCache.getInstance().get(pk_user);
							List<DZFSession> listRedisSession = new ArrayList<DZFSession>();
							
							boolean bFound = false;						//成功从redis服务器找到session标志
							DZFSession dzfCacheSession = null;
							if (sessionvo != null)
							{
								DZFSession[] sessions = sessionvo.getSessions();
								for (DZFSession cachesession : sessions)
								{
									if (bFound == false && appid.equals(cachesession.getAppid()) && remoteIp.equals(req.getRemoteAddr()))
									{
										dzfCacheSession = cachesession;
										bFound = true;
									}
									else
									{
										listRedisSession.add(cachesession);
									}
								}
							}
							if (bFound)	//redis服务器上有在线用户信息
							{
								//服务器session没有用户信息，重新自动登录
								//这种情况可能是集群转发服务器发生了跳转，跳至新服务器，或者应用服务器重启，redis服务器与客户端还正常运行
								if (DzfSessionContext.getInstance().getSessionByPkUser(pk_user) != null && !DzfSessionContext.getInstance().getSessionByPkUser(pk_user).getId().equals(session.getId())) 
			                	{
			                		DzfSessionContext.getInstance().DelUserSessionByPkUser(pk_user, false);
			                	}
								//把redis缓存信息重新赋值给httpsession
								DzfSessionTool.fillValueToHttpSession(dzfCacheSession, session);
	
								DzfSessionContext.getInstance().AddUserSession(session);
								RSACoderUtils.createToken(session);
		
							}
							else
							{
								//客户端由token，但应用服务器无session，redis缓存服务器也没有登录信息，不能登录
								bDeleteCookie = true;
							}
						}
						else		//有应用服务器用户
						{
							//应用服务器上有用户信息，只需判断token用户与应用服务器用户是否一致即可
							if (sessionUser.equals(pk_user) == false)
							{
								//这种情况应该是客户端由A用户登录成功过，又由B用户成功登录，但B用户信息没有成功写到客户端COOKIE，
								//这时A用户属于退出状态，B用户登录又不完整，不能算成功登录，所以需要清缓存
								bDeleteSession = true;
								bDeleteCookie = true;
							}
							else
							{
								//一切正常的交互，不需做什么工作，向redis服务器同步session信息在定时服务中运行，不能在每次客户端请求都做。
							}
						}
					}
					else
					{
						//没有成功解出真正token内容，可能是客户端无cookie，或cookie已陈旧，与服务器不匹配，
						bDeleteCookie = true;
						bDeleteSession = true;
					}
				}
						
				
	//session修改开始结束
				
				
				
				//SSOServer跳转
				
				String contextpath = req.getContextPath();
				String longurl = req.getRequestURL().toString();
				String pk_user = (String)session.getAttribute(IGlobalConstants.login_user);
				String ticket = request.getParameter("t");
				String qz = request.getParameter("qz");	//提示信息
//				if (StringUtil.isEmptyWithTrim(pk_user) && (longurl.indexOf("/login.jsp") >= 0 || longurl.endsWith(contextpath) || longurl.endsWith(contextpath + "/")))
				if (StringUtil.isEmptyWithTrim(pk_user))
				{
					String path = this.getClass().getClassLoader().getResource("").getPath();
		
				
					String regex = "(WEB-INF)[\\/\\\\]{1}(classes)";
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(path);
					
					if(m.find()) {
						
						path = path.substring(0, m.start());
					}
	
					String os = System.getProperties().getProperty("os.name");
					if (path.startsWith("/") && os != null && os.toLowerCase().startsWith("win"))
					{
						path = path.substring(1);
					}
					File f = new File(path + "login.jsp");
	
					if (f.exists() == false)	//没有login.jsp则是通过ssoserver统一登录
					{
						//检查是否有统一登录服务器配置
						String ssoserver = getSSOServerAddress();
						if (ssoserver == null)
						{
							errorRetMsg = "请配置统一登录服务器";
							return;
						}
						
//						if (StringUtil.isEmptyWithTrim(pk_user))
//						{
							if (StringUtil.isEmptyWithTrim(ticket) == false)
							{
								boolean isSuccess = false;
								DZFSession ticketobj = null;
								
								String ssoserver_url = ssoserver + "/TicketServlet";
              
				            	Map<String, String> map = new HashMap<String, String>();
				            	map.put("ticket", ticket);
				            	
				                try {
				                	String ticketobjstr = new HttpClientUtil().doPostEntity(ssoserver_url, map, "utf-8");
				                	if (StringUtil.isEmptyWithTrim(ticketobjstr) == false) {
				                		ticketobj = new ObjectMapper().readValue(ticketobjstr, DZFSession.class);
				                		if (StringUtil.isEmptyWithTrim(ticketobj.getPk_user()) == false)
				                		{
				                			isSuccess = true;
				                		}
				                	}
					               
				                } catch (Exception e) {
				                	//filter 写不写log？
				                }
				                if (isSuccess)
				                {
				                	pk_user = ticketobj.getPk_user();
	
									DzfSessionTool.fillValueToHttpSession(ticketobj, session);
									
									DzfSessionContext.getInstance().AddUserSession(session);
									
				                	if (DzfSessionContext.getInstance().getSessionByPkUser(pk_user) != null && !DzfSessionContext.getInstance().getSessionByPkUser(pk_user).getId().equals(session.getId())) 
				                	{
				                		DzfSessionContext.getInstance().DelUserSessionByPkUser(pk_user, false);
				                	}
									
									RSACoderUtils.createToken(session);
									//写登录成功信息到客户端
									DzfCookieTool.writeCookie(session, request, response);
									
									//走到这里，cookie和session已不能删除了。
									bDeleteCookie = false;	
									bDeleteSession = false;
									
									int iIndex = longurl.indexOf(contextpath);
									res.sendRedirect(longurl.substring(0, iIndex + contextpath.length()) + (qz == null ? "" : "?qz=" + qz));

				                }
				                else
				                {
				                	errorRetMsg = "无权操作,请联系管理员";
							
									bDeleteCookie = true;	
									bDeleteSession = true;
			
				                }
							}
							else
							{
								String encoderURL = URLEncoder.encode(longurl, "UTF-8");
								//没有用户，也没有ticket
								//跳转至ssoserver用户登录
								res.sendRedirect(ssoserver + "/login_kj.jsp?service=" + encoderURL);
								
							}
							return;
//						}
//						else
//						{
//							//已经登录成功，什么都不用做
//						}
					}
					else
					{
						//开发调试过程有login.jsp, 保留原交互
					}
				}
				else
				{
					//用户已经登陆成功，如果有ticket后缀，则跳转
					if (StringUtil.isEmptyWithTrim(ticket) == false)
					{
						int iIndex = longurl.indexOf(contextpath);
						res.sendRedirect(longurl.substring(0, iIndex + contextpath.length()) + (qz == null ? "" : "?qz=" + qz));
						return;
					}
				}
			}
			catch (Exception e)
			{
				//filter写日志否？
			}
			finally {
				if (bDeleteCookie)
				{
					//删除cookie
					DzfCookieTool.deleteCookie(request, response);
				}
				if (bDeleteSession)
				{
					if (session.getAttribute(IGlobalConstants.login_user) != null)
					{
						DzfSessionTool.clearSession(session);
					}
				}
				if (errorRetMsg != null)
				{
					res.getWriter().write("{\"success\":false,\"msg\":\"" + errorRetMsg + "\",\"status\":-300}");
					res.getWriter().flush();
					res.getWriter().close();
					return;
				}
			}

			////ssoserver跳转 结束
			
			String url = req.getRequestURI();
			if( req!=null && (url.endsWith("DzfApplet.jar")||url.endsWith("fileupload.jar") || url.endsWith(".css") || url.endsWith(".js")  || url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".gif"))){
				filterChain.doFilter(request, response);
	        	return;
	        }
			//数据库监控
			if(url.contains("/druid/") && 
					(url.endsWith(".html") || url.endsWith("druid/submitLogin") || url.endsWith(".json"))){
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
			//小薇无忧app登陆
			if(url.contains("/xwwy_app/busidata!dealData.action")){
				filterChain.doFilter(request, response);
	        	return;
			}
			//增加中服直接登录
			if(url.endsWith("/sys/auto_user!autologin.action") || url.endsWith("/zonefulogin.jsp") || url.endsWith("/bindzflogin.jsp")){
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
		    	
		    	if(! url.endsWith("sm_user!logout.action") &&  ! url.endsWith("register_act!saveRegistered.action") && !url.endsWith("sm_user!updatePwdLogin.action") && ! url.endsWith("forgot_password.jsp") && ! url.endsWith("dz_registered.jsp") && (userid == null || (corp == null && !url.endsWith("/selcomp.jsp") && !url.endsWith("/sys/sm_user!gsSelect.action") && !url.endsWith("/sys/sm_user!gsQuery.action"))) && !url.endsWith("/sys/sm_user!login.action") && !url.endsWith("/sys/sm_user!getLogin.action") && !url.endsWith("/sys/sm_user!loginForFactory.action") &&!url.endsWith("/fct/fct_statistics!query.action")&& !url.endsWith("/sys/sm_user!login2.action") && !url.endsWith("/sys/sm_user!dzfLogin.action") && !url.endsWith("/searchPsw.jsp") && !url.endsWith("/st/searchPsw!getPswBack.action") && !url.endsWith("/st/searchPsw!sandYZcode.action")){
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
	private String getSSOServerAddress()
	{
		String server = null;
		 Properties prop = new Properties();   
		 InputStream in = null;
		 try{
			 //读取属性文件ssoserver.properties
			 in =  this.getClass().getResourceAsStream("/ssoserver.properties");

			 prop.load(in);     ///加载属性列表
			 server = prop.getProperty("server");
			 
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
		 return server;
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
