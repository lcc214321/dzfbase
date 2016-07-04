package com.dzf.pub.cache;

import com.dzf.pub.WeakHashLock;

public class RsaKeyLock extends WeakHashLock<String> {
	
	private static RsaKeyLock lock = new RsaKeyLock();
	
	private RsaKeyLock() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public static RsaKeyLock getInstance(){
		return lock;
	}
}
