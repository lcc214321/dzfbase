package com.dzf.dao.bs;

import com.dzf.dao.jdbc.framework.SQLParameter;
import com.dzf.pub.StringUtil;

public class BatchIdsBO<T> {

	public BatchIdsBO() {
		// TODO Auto-generated constructor stub
	}
public void exec(T[] ts,IBatchIds ibi){
	int len=ts==null?0:ts.length;
	String str;
	int len1=0;
	SQLParameter sp=new SQLParameter();
	for (int i = 0; i < len; i+=200) {
		len1=(i+200>len)?len:i+200;
		str=StringUtil.toString(ts, i, len1);
		
		for (int j = i; j < len1; j++) {
			sp.addParam(ts[j]);
		}
		ibi.Exec(str,sp);
		sp.clearParams();
	}
}
}
