package com.dzf.pub.sync;

public class SyncDataModel implements java.io.Serializable{

	
	private boolean issusscess = true;
	
	private String errorinfo = null;
	
	private Object obj = null;

	public boolean isIssusscess() {
		return issusscess;
	}

	public void setIssusscess(boolean issusscess) {
		this.issusscess = issusscess;
	}

	public String getErrorinfo() {
		return errorinfo;
	}

	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
