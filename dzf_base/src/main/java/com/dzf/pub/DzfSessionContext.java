package com.dzf.pub;

import java.util.HashMap;
import javax.servlet.http.HttpSession;

public class DzfSessionContext {
	private static DzfSessionContext instance;
	private HashMap<String, HttpSession> mymap;
	private HashMap<String, HttpSession> myUserMap;

	private DzfSessionContext() {
		mymap = new HashMap<String, HttpSession>();
		myUserMap = new HashMap<String, HttpSession>();
	}

	public static DzfSessionContext getInstance() {
		if (instance == null) {
			instance = new DzfSessionContext();
		}
		return instance;
	}

	public synchronized void AddSession(HttpSession session) {
		if (session != null) {
			mymap.put(session.getId(), session);
		}
	}
	
	public synchronized void AddUserSession(HttpSession session) {
		if (session != null) {
			String pk_user = (String)session.getAttribute(IGlobalConstants.login_user);
			if(pk_user != null){
				if(myUserMap.get(pk_user) != null){
//					先销毁
					DelUserSession(myUserMap.get(pk_user));
				}
				myUserMap.put(pk_user, session );
			}
		}
	}
	
	public synchronized void AddUserSession(HttpSession session,String pk_user) {
		if (session != null) {
			if(pk_user != null){
				if(myUserMap.get(pk_user) != null){
//					先销毁
					DelUserSession(myUserMap.get(pk_user));
				}
				myUserMap.put(pk_user, session );
			}
		}
	}
	

	public synchronized void DelSession(HttpSession session) {
		if (session != null) {
			String pk_user = (String)session.getAttribute(IGlobalConstants.login_user);
			if(pk_user != null){
				DelUserSession(session);
			}
			mymap.remove(pk_user);
		}
	}

	public synchronized void DelUserSession(HttpSession session) {
		if (session != null) {
			String pk_user = (String)session.getAttribute(IGlobalConstants.login_user);
			if(pk_user != null){
				if(myUserMap.get(pk_user) != null){
					//清空Session
					myUserMap.remove(pk_user);
				}
			}
		}
	}

	public synchronized void DelUserSessionByPkUser(String pk_user,boolean isLogout) {
			if(pk_user != null){
				if(!isLogout && myUserMap.get(pk_user) != null){
//					DelSession(myUserMap.get(pk_user));
					myUserMap.get(pk_user).removeAttribute(IGlobalConstants.login_user);
					myUserMap.get(pk_user).removeAttribute(IGlobalConstants.login_date);
					myUserMap.get(pk_user).removeAttribute(IGlobalConstants.login_corp);
					myUserMap.get(pk_user).setAttribute(IGlobalConstants.logout_msg,"被其它用户强制退出！");
					//清空Session
				}
				myUserMap.remove(pk_user);
			}
	}

	public synchronized HttpSession getSession(String session_id) {
		if (session_id == null)
			return null;
		return (HttpSession) mymap.get(session_id);
	}

	public synchronized HttpSession getSessionByPkUser(String pk_user) {
		if (pk_user == null)
			return null;
		return  myUserMap.get(pk_user);
	}

}
