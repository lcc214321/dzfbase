package com.dzf.pub.session;

import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.UrlBase64;

import com.dzf.framework.rsa.Encode;
import com.dzf.pub.IGlobalConstants;
import com.dzf.pub.StringUtil;
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
			if (cookies != null && cookies.length > 0)
			{
				for (Cookie cookie : cookies) {
					if ("dzfsso".equals(cookie.getName())) {
						return cookie.getValue();
					}
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
	
	public static String getUUIDByCookie(ServletRequest request) 
	{
		String encriptuuid = null;
		String uuid = null;
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();
		try {
			if (cookies != null && cookies.length > 0)
			{
				for (Cookie cookie : cookies) {
					if ("dzfuid".equals(cookie.getName())) {
						encriptuuid = cookie.getValue();
						break;
					}
				}
				if (encriptuuid != null)
				{
					uuid = new String(RSACoder.decryptByPrivateKey(UrlBase64.decode(encriptuuid.getBytes()), RsaKeyCache.getInstance().getPrivateKey()));
				}
			}
		}
		catch (Exception e)
		{
			Logger log = Logger.getLogger((new DzfCookieTool()).getClass());
			log.error(e);
		}
		return uuid;
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
	 * 删除客户端cookie中appid信息
	 * @param request
	 * @param response
	 */
	public static void deleteCookie(ServletRequest request, ServletResponse response)
	{
		String contentPath = ((HttpServletRequest)request).getContextPath() + "/";
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();
		if (cookies != null && cookies.length > 0)
		{
			for (Cookie cookie : cookies) {
				if ("dzfsso".equals(cookie.getName())) 
				{
					cookie.getValue();
					cookie.setMaxAge(0);
					cookie.setPath(contentPath);
					((HttpServletResponse)response).addCookie(cookie);
				}
				else if ("dzfcorp".equals(cookie.getName())) {
					cookie.setMaxAge(0);
					cookie.setPath(contentPath);
					((HttpServletResponse)response).addCookie(cookie);
				} 
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
		String pk_user = (String)session.getAttribute(IGlobalConstants.login_user);
		
		String appid = (String)session.getAttribute(IGlobalConstants.appid);
		
		String clientid = (String)session.getAttribute(IGlobalConstants.clientid);
		
		String contentPath = ((HttpServletRequest)request).getContextPath() + "/";
		
		String strUUID = (String)session.getAttribute(IGlobalConstants.uuid);
		if (strUUID == null)
		{
			UUID uuid = UUID.randomUUID();
		    strUUID = uuid.toString(); 
		    session.setAttribute(IGlobalConstants.uuid, strUUID);
		}
		
		StringBuffer sb = new StringBuffer();
	    
		sb.append(strUUID);
		sb.append(",");
		sb.append(pk_user);
		
		sb.append(",");
		sb.append(appid);
		if(!StringUtil.isEmpty(clientid)){
			sb.append(",");
			sb.append(clientid);
		}

		try {
			String encryptToken = new String(UrlBase64.encode(RSACoder.encryptByPublicKey(sb.toString().getBytes(), RsaKeyCache.getInstance().getPublicKey())));
			
			Cookie cookie = new Cookie("dzfsso", encryptToken);
			cookie.setPath(contentPath);
			((HttpServletResponse)response).addCookie(cookie);
			
			String pk_corp = (String)session.getAttribute(IGlobalConstants.login_corp);
			pk_corp = (pk_corp == null ? "nullxx" : pk_corp);
			
			//公司cookie，des加密
			Encode DesEncoder = new Encode();
			String encryptPk_corp =  new String(UrlBase64.encode(DesEncoder.encode(pk_corp).getBytes()));
			cookie = new Cookie("dzfcorp", encryptPk_corp);
			cookie.setPath(contentPath);
			((HttpServletResponse)response).addCookie(cookie);
			
		}
		catch  (Exception e)
		{
			Logger log = Logger.getLogger((new DzfCookieTool()).getClass());
			log.error(e);
		}
	}
	/**
	 * 写存放uuid的cookie，只有uuid一个值
	 * @param session
	 * @param request
	 * @param response
	 */
	public static String writeCookie_UUID(HttpSession session, ServletRequest request, ServletResponse response)
	{

		String contentPath = ((HttpServletRequest)request).getContextPath() + "/";
		
		String strUUID = (String)session.getAttribute(IGlobalConstants.uuid);
		if (strUUID == null)
		{
			UUID uuid = UUID.randomUUID();
		    strUUID = uuid.toString(); 
		    session.setAttribute(IGlobalConstants.uuid, strUUID);
		}
		
		try {
			String encryptToken = new String(UrlBase64.encode(RSACoder.encryptByPublicKey(strUUID.getBytes(), RsaKeyCache.getInstance().getPublicKey())));
			
			Cookie cookie = new Cookie("dzfuid", encryptToken);
			cookie.setPath(contentPath);
			((HttpServletResponse)response).addCookie(cookie);
			
		}
		catch  (Exception e)
		{
			Logger log = Logger.getLogger((new DzfCookieTool()).getClass());
			log.error(e);
		}
		return strUUID;
	}
//	/**
//	 * 删除uuid的cookie 
//	 * @param request
//	 * @param response
//	 */
//	public static void deleteCookie_UUID(ServletRequest request, ServletResponse response)
//	{
//		String contentPath = ((HttpServletRequest)request).getContextPath() + "/";
//		Cookie[] cookies = ((HttpServletRequest)request).getCookies();
//		
//		for (Cookie cookie : cookies) {
//			if ("dzfuid".equals(cookie.getName())) 
//			{
//				cookie.getValue();
//				cookie.setMaxAge(0);
//				cookie.setPath(contentPath);
//				((HttpServletResponse)response).addCookie(cookie);
//			}
//		}
//	}
}
