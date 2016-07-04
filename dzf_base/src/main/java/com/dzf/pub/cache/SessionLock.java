package com.dzf.pub.cache;

import com.dzf.pub.WeakHashLock;

public class SessionLock extends WeakHashLock<String> {
	
	private static SessionLock lock = new SessionLock();
	
	private SessionLock() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public static SessionLock getInstance(){
		return lock;
	}
}
