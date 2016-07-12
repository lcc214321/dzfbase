package com.dzf.pub;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DzfSessionContext {
	
	private static Logger log = Logger.getLogger(DzfSessionContext.class.getName());
	
	private static DzfSessionContext instance = null;
	private ConcurrentHashMap<String, HttpSession> mymap;
	private ConcurrentHashMap<String, HttpSession> myUserMap;

	private DzfSessionContext() {
		mymap = new ConcurrentHashMap<String, HttpSession>();
		myUserMap = new ConcurrentHashMap<String, HttpSession>();
	}

	public static DzfSessionContext getInstance() {
		if (instance == null)
		{
			instance = new DzfSessionContext();
			ScheduledThreadPoolExecutor stexec = new ScheduledThreadPoolExecutor(2);
			//同步session到redis服务器
			stexec.scheduleAtFixedRate(new DzfSessionRedisSynchronizeService(instance), 1, 1, TimeUnit.MINUTES);	///延迟一分钟启动，间隔2分钟
			//清理ssoserver的ticket map, 无redis缓存时才使用该hashmap
			stexec.scheduleAtFixedRate(new DzfTicketClearService(), 5, 10, TimeUnit.MINUTES);	///延迟5分钟启动，间隔10分钟
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

//  session失效
	public synchronized void DelSession(HttpSession session) {
		if (session != null) {
			String pk_user = (String)session.getAttribute(IGlobalConstants.login_user);
			if(pk_user != null){
				DelUserSession(session);
			}
			mymap.remove(session.getId());  
		}
	}

//	session失效清除用户
	public synchronized void DelUserSession(HttpSession session) {
		if (session != null) {
			String pk_user = (String)session.getAttribute(IGlobalConstants.login_user);
			if(pk_user != null){
				if(myUserMap.get(pk_user) != null){
					//清空Session
					myUserMap.remove(pk_user);
					/*try{
						LoginLogVo loginLogVo =  new LoginLogVo();
						loginLogVo.setLogindate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
						loginLogVo.setLoginsession(session.getId());
						loginLogVo.setLoginstatus(0);
						loginLogVo.setPk_user(pk_user);
						loginLogVo.setPk_corp((String)session.getAttribute(IGlobalConstants.login_corp));
						loginLogVo.setLoginstatus(3);
						getUserService().logoutLog(loginLogVo);
					}catch(Exception e){
						e.printStackTrace();
					}*/
				}
			}
		}
	}

//	isLogout，ture:用户自己退出,false:被其它用户强制退出
	public synchronized void DelUserSessionByPkUser(String pk_user,boolean isLogout) {
			if(pk_user != null){
				try{
					if(!isLogout && myUserMap.get(pk_user) != null){
						/*try{
							LoginLogVo loginLogVo =  new LoginLogVo();
							loginLogVo.setLogindate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
							loginLogVo.setLoginsession(myUserMap.get(pk_user).getId());
							loginLogVo.setLoginstatus(0);
							loginLogVo.setPk_user(pk_user);
							loginLogVo.setPk_corp((String)myUserMap.get(pk_user).getAttribute(IGlobalConstants.login_corp));
							loginLogVo.setLoginstatus(2);
							getUserService().logoutLog(loginLogVo);
						}catch(Exception e){
							e.printStackTrace();
						}*/
						
//						DelSession(myUserMap.get(pk_user));
						myUserMap.get(pk_user).removeAttribute(IGlobalConstants.login_user);
						myUserMap.get(pk_user).removeAttribute(IGlobalConstants.login_date);
						myUserMap.get(pk_user).removeAttribute(IGlobalConstants.login_corp);
						myUserMap.get(pk_user).setAttribute(IGlobalConstants.logout_msg,"被其它用户强制退出！");
						//清空Session
					}
				}catch(Exception e){
					log.error(e);
				}
				try{
					myUserMap.remove(pk_user);
				}catch(Exception e){
					log.error(e);
				}
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

	public synchronized ConcurrentHashMap<String, HttpSession> getMymap() {
		return mymap;
	}





}
