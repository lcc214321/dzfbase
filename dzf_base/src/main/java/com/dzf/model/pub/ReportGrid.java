package com.dzf.model.pub;

/**
 * 报表的显示字段
 * 
 * @author zhangj
 * 
 */
public class ReportGrid extends Grid {

	private String bsh;//凭证数

	private String pzqj;//凭证期间

	public String getBsh() {
		return bsh;
	}

	public void setBsh(String bsh) {
		this.bsh = bsh;
	}

	public String getPzqj() {
		return pzqj;
	}

	public void setPzqj(String pzqj) {
		this.pzqj = pzqj;
	}

}
