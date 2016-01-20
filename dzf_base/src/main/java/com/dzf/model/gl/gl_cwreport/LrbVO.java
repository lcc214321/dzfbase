package com.dzf.model.gl.gl_cwreport;

import com.dzf.pub.SuperVO;
import com.dzf.pub.lang.DZFDouble;

/**
 * 利润表
 * @author JasonLiu
 *
 */
public class LrbVO extends SuperVO {

	//项目
	public String xm ;
	
	//行数
	public String hs ;
	
	//本年累计金额	
	public DZFDouble bnljje ;
	
	//本月金额
	public DZFDouble byje ;
	
	private Integer level;
	
	//打印时  标题显示的区间区间
	public String titlePeriod;
	// 公司
	public String gs;
	
	public String period;//期间
	@Override
	public String getPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public DZFDouble getBnljje() {
		return bnljje;
	}

	public void setBnljje(DZFDouble bnljje) {
		this.bnljje = bnljje;
	}

	public DZFDouble getByje() {
		return byje;
	}

	public void setByje(DZFDouble byje) {
		this.byje = byje;
	}

	public String getHs() {
		return hs;
	}

	public void setHs(String hs) {
		this.hs = hs;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getTitlePeriod() {
		return titlePeriod;
	}

	public void setTitlePeriod(String titlePeriod) {
		this.titlePeriod = titlePeriod;
	}

	public String getGs() {
		return gs;
	}

	public void setGs(String gs) {
		this.gs = gs;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

}
