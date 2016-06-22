package com.dzf.pub.cache;

import com.dzf.pub.WeakHashLock;

public class UserLock extends WeakHashLock<String> {
	private UserLock() {
		super();
		// TODO Auto-generated constructor stub
	}
	private static UserLock lock=new UserLock();
public static UserLock getInstance(){
	return lock;
}
}
