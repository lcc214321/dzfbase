package com.dzf.pub.cache;

import com.dzf.pub.WeakHashLock;

public class BusinessDataLock extends WeakHashLock<String> {
	
	private static BusinessDataLock lock = new BusinessDataLock();
	
	private BusinessDataLock() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public static BusinessDataLock getInstance(){
		return lock;
	}
}
