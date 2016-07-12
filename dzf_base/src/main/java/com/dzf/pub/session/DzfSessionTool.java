package com.dzf.pub.session;

import java.util.Set;

import javax.servlet.http.HttpSession;

import com.dzf.model.pub.DZFSessionVO;
import com.dzf.pub.DzfSessionContext;
import com.dzf.pub.IGlobalConstants;
import com.dzf.pub.StringUtil;

public class DzfSessionTool {

	public DzfSessionTool() {
		// TODO Auto-generated constructor stub
	}
	public static DZFSessionVO createSession(HttpSession httpsession)
	{
		DZFSessionVO session = new DZFSessionVO();
		session.setUuid((String)httpsession.getAttribute(IGlobalConstants.uuid));
		session.setPk_user((String)httpsession.getAttribute(IGlobalConstants.login_user));
		session.setPk_corp((String)httpsession.getAttribute(IGlobalConstants.login_corp));
		session.setAppid((String)httpsession.getAttribute(IGlobalConstants.appid));
		session.setDate((String)httpsession.getAttribute(IGlobalConstants.login_date));
		session.setDzfMap((Set<Integer>)httpsession.getAttribute(IGlobalConstants.POWER_MAP));
		session.setLasttime(httpsession.getLastAccessedTime());
		session.setRemoteIp((String)httpsession.getAttribute(IGlobalConstants.remote_address));
		session.setSessionid(httpsession.getId());
		session.setToken((String)httpsession.getAttribute(IGlobalConstants.login_token));
		session.setXwwy_sessionid((String)httpsession.getAttribute("xwwy_sessionid"));
		return session;
		
	}
	public static void fillValueToHttpSession(DZFSessionVO dzfsession, HttpSession httpsession)
	{
		httpsession.setAttribute(IGlobalConstants.uuid, dzfsession.getUuid());
		httpsession.setAttribute(IGlobalConstants.login_user, dzfsession.getPk_user());

		httpsession.setAttribute(IGlobalConstants.appid, dzfsession.getAppid());
		httpsession.setAttribute(IGlobalConstants.login_date, dzfsession.getDate());
		if (dzfsession.getDzfMap() != null)
		{
			httpsession.setAttribute(IGlobalConstants.POWER_MAP, dzfsession.getDzfMap());
		}
		httpsession.setAttribute(IGlobalConstants.remote_address, dzfsession.getRemoteIp());
		if (dzfsession.getPk_corp() != null)
		{
			httpsession.setAttribute(IGlobalConstants.login_corp, dzfsession.getPk_corp());
		}
		if (dzfsession.getToken() != null)
		{
			httpsession.setAttribute(IGlobalConstants.login_token, dzfsession.getToken());
		}
		if (dzfsession.getXwwy_sessionid() != null)
		{
			httpsession.setAttribute("xwwy_sessionid", dzfsession.getXwwy_sessionid());
		}
	}
	public static void clearSession(HttpSession httpsession)
	{
		
		//退出的信息也要清除
		
		httpsession.removeAttribute(IGlobalConstants.uuid);
		httpsession.removeAttribute(IGlobalConstants.login_user);
		httpsession.removeAttribute(IGlobalConstants.appid);
		httpsession.removeAttribute(IGlobalConstants.login_date);
		if (httpsession.getAttribute(IGlobalConstants.POWER_MAP) != null)
		{
			httpsession.removeAttribute(IGlobalConstants.POWER_MAP);
		}
		httpsession.removeAttribute(IGlobalConstants.remote_address);
		if (httpsession.getAttribute(IGlobalConstants.login_corp) != null)
		{
			httpsession.removeAttribute(IGlobalConstants.login_corp);
		}
		if (httpsession.getAttribute(IGlobalConstants.login_token) != null)
		{
			httpsession.removeAttribute(IGlobalConstants.login_token);
		}
		if (httpsession.getAttribute("xwwy_sessionid") != null)
		{
			httpsession.removeAttribute("xwwy_sessionid");
		}
		DzfSessionContext.getInstance().DelSession(httpsession);
		
		
	}
}
