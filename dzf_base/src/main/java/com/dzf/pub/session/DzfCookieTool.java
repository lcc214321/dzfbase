package com.dzf.pub.session;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.UrlBase64;

import com.dzf.framework.rsa.DES;
import com.dzf.framework.rsa.Encode;
import com.dzf.pub.IGlobalConstants;
import com.dzf.pub.cache.RsaKeyCache;
import com.dzf.pub.framework.rsa.RSACoder;

public class DzfCookieTool {

	public DzfCookieTool() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 读取客户端cookie中token信息
	 * @param request
	 * @return
	 */
	public static String getToken(ServletRequest request) 
	{
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();
		try {
			for (Cookie cookie : cookies) {
				if ("dzfsso".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		catch (Exception e)
		{
			Logger log = Logger.getLogger((new DzfCookieTool()).getClass());
			log.error(e);
		}
		return null;
	}
	/**
	 * 读取客户端cookie中解密的token信息
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getRealToken(String token) 
	{
		try {
			return new String(RSACoder.decryptByPrivateKey(UrlBase64.decode(token.getBytes()), RsaKeyCache.getInstance().getPrivateKey()));
		}
		catch (Exception e)
		{
			Logger log = Logger.getLogger((new DzfCookieTool()).getClass());
			log.error(e);
		}
		return null;
	}
	/**
	 * 删除客户端cookie
	 * @param request
	 * @param response
	 */
	public static void deleteCookie(ServletRequest request, ServletResponse response)
	{
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();
		for (Cookie cookie : cookies) {
			if ("dzfsso".equals(cookie.getName())) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				((HttpServletResponse)response).addCookie(cookie);
			}else
			if ("dzfcorp".equals(cookie.getName())) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				((HttpServletResponse)response).addCookie(cookie);
			} 
		}
	}
	/**
	 * 
	 * @param session
	 * @param request
	 * @param response
	 */
	public static void writeCookie(HttpSession session, ServletRequest request, ServletResponse response)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(request.getRemoteAddr());
		sb.append(",");
		sb.append((String)session.getAttribute(IGlobalConstants.login_user));
		sb.append(",");
		sb.append((String)session.getAttribute(IGlobalConstants.appid));
		try {
			String encryptToken = new String(UrlBase64.encode(RSACoder.encryptByPublicKey(sb.toString().getBytes(), RsaKeyCache.getInstance().getPublicKey())));
			
			Cookie cookie = new Cookie("dzfsso", encryptToken);
			cookie.setPath("/");
			((HttpServletResponse)response).addCookie(cookie);
			
			String pk_corp = (String)session.getAttribute(IGlobalConstants.login_corp);
			pk_corp = (pk_corp == null ? "nullxx" : pk_corp);
			//公司cookie，des加密
			Encode DesEncoder = new Encode();
			String encryptPk_corp =  new String(UrlBase64.encode(DesEncoder.encode(pk_corp).getBytes()));
			cookie = new Cookie("dzfcorp", encryptPk_corp);
			cookie.setPath("/");
			((HttpServletResponse)response).addCookie(cookie);
		}
		catch  (Exception e)
		{
			Logger log = Logger.getLogger((new DzfCookieTool()).getClass());
			log.error(e);
		}
	}
}
