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
		//这里存在问题。如果这个session过了有效期，那么session.getAttribute(IGlobalConstants.login_user)
		//会报java.lang.IllegalStateException: getAttribute: Session already invalidated，错误。
		//因此根据session取值，需判断该session是否有效。
		//一般用户前台注销时，传回后台会调用session.invalidate();方法。因此这里存在map中的session就失效了，就不能getAttribute了。
		////
		//这里这样修改的原因是:com.dzf.pub.DzfSessionListener在方法中sessionDestroyed中已经调用了DelSession(session)，
		//其它地方调用，则需判断一下当前sessiona是否有效即可。但HttpSession是一个接口，无法调用判断是否失效方法，因此这里通过
		//捕获异常得到
		//if(session.isValid()) ----StandardSession
		//return;
		try{
			String pk_user = (String)session.getAttribute(IGlobalConstants.login_user);
			if(pk_user != null){
				DelUserSession(session);
			}
			mymap.remove(session.getId());
		}catch(IllegalStateException e){//此异常吃掉
			log.error("当前session已经失效！",e);
		}

	}

//	session失效清除用户
	public synchronized void DelUserSession(HttpSession session) {
		try{
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
						//e.printStackTrace();
					}*/
				}
			}
		}catch(IllegalStateException e){//此异常吃掉
			log.error("当前session已经失效！",e);
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
							//e.printStackTrace();
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