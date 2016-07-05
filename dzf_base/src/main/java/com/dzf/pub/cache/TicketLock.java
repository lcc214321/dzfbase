package com.dzf.pub.cache;

import com.dzf.pub.WeakHashLock;

public class TicketLock extends WeakHashLock<String> {
	
	private static TicketLock lock = new TicketLock();
	
	private TicketLock() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public static TicketLock getInstance(){
		return lock;
	}
}
