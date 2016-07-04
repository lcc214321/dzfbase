package com.dzf.pub.session;

import java.io.Serializable;
import java.util.Set;


public class DZFSession implements Serializable {

	private String pk_corp;
	private String pk_user;
	private String date;
	private Set<Integer> dzfMap;
	private String sessionid; 
	private String remoteIp;
	private long lasttime;	
	private String appid;
	
	//小微无忧
	private String xwwy_sessionid;
	private String token;
	
	public DZFSession() {
		// TODO Auto-generated constructor stub
		
	}
	public String getPk_corp() {
		return pk_corp;
	}
	public String getPk_user() {
		return pk_user;
	}
	public String getDate() {
		return date;
	}
	public Set<Integer> getDzfMap() {
		return dzfMap;
	}
	public String getSessionid() {
		return sessionid;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}
	public void setPk_user(String pk_user) {
		this.pk_user = pk_user;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setDzfMap(Set<Integer> dzfMap) {
		this.dzfMap = dzfMap;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	public long getLasttime() {
		return lasttime;
	}
	public void setLasttime(long lasttime) {
		this.lasttime = lasttime;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getXwwy_sessionid() {
		return xwwy_sessionid;
	}
	public String getToken() {
		return token;
	}
	public void setXwwy_sessionid(String xwwy_sessionid) {
		this.xwwy_sessionid = xwwy_sessionid;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
