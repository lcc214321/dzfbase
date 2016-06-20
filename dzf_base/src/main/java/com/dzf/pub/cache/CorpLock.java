package com.dzf.pub.cache;

import com.dzf.pub.WeakHashLock;

public class CorpLock extends WeakHashLock<String> {
	private CorpLock() {
		super();
		// TODO Auto-generated constructor stub
	}
	private static CorpLock lock=new CorpLock();
public static CorpLock getInstance(){
	return lock;
}
}
