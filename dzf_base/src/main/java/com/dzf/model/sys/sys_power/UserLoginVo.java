package com.dzf.model.sys.sys_power;

import java.util.Date;

public class UserLoginVo {
	
	private int number;	//次数
	private Date login_date;	//最后登录时间
	 
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNumber() {
		return number;
	}
	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}
	public Date getLogin_date() {
		return login_date;
	}
	


}
