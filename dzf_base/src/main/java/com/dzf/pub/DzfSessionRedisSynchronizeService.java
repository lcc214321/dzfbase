package com.dzf.pub;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import com.dzf.pub.cache.SessionCache;

public class DzfSessionRedisSynchronizeService extends TimerTask {

	DzfSessionContext sessionContext = null;
	public DzfSessionRedisSynchronizeService(DzfSessionContext sessionContext) {
		// TODO Auto-generated constructor stub
		this.sessionContext = sessionContext;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//同步session
		ConcurrentHashMap<String, HttpSession> myMap = sessionContext.getMymap();
		HttpSession session = null;
		String pk_user = null;
		for (String sessionid : myMap.keySet())
		{
			session = myMap.get(sessionid);
			
			pk_user = (String)session.getAttribute(IGlobalConstants.login_user);
			if (StringUtil.isEmptyWithTrim(pk_user))
			{
				continue;
			}
			
			SessionCache.getInstance().addSession(session);
		}

	}
}
