package com.dzf.model.gl.gl_kmreport;

import com.dzf.pub.SuperVO;
import com.dzf.pub.lang.DZFDouble;


public class KmMxZVO extends SuperVO {
	private String pk_tzpz_h;
	public String getPk_tzpz_h() {
		return pk_tzpz_h;
	}

	public void setPk_tzpz_h(String pk_tzpz_h) {
		this.pk_tzpz_h = pk_tzpz_h;
	}
	private String otsubject;
	public String getOtsubject() {
		return otsubject;
	}

	public void setOtsubject(String otsubject) {
		this.otsubject = otsubject;
	}
	
	//打印时  标题显示的区间区间
	public String titlePeriod;
	
	public String gs;
	
	// 日期
	public String rq;

	// 凭证号
	public String pzh;

	// 摘要
	public String zy;

	// 借方
	public DZFDouble jf;

	// 贷方
	public DZFDouble df;

	// 方向
	public String fx;

	private Integer level;

	private String day;// 本月，本年的天数
	// 余额
	public DZFDouble ye;
	public String kmbm;
	
	private String pk_accsubj;
	
	public String isPaging;  //是否分页  Y/N
	

	// 科目
	public String km;

	private String bz;
	
	public String pzpk;

	public String getRq() {
		return rq;
	}

	public void setRq(String rq) {
		this.rq = rq;
	}

	public String getPzh() {
		return pzh;
	}

	public void setPzh(String pzh) {
		this.pzh = pzh;
	}

	public String getZy() {
		return zy;
	}

	public void setZy(String zy) {
		this.zy = zy;
	}

	public DZFDouble getJf() {
		return jf;
	}

	public void setJf(DZFDouble jf) {
		this.jf = jf;
	}

	public DZFDouble getDf() {
		return df;
	}

	public void setDf(DZFDouble df) {
		this.df = df;
	}

	public String getFx() {
		return fx;
	}

	public void setFx(String fx) {
		this.fx = fx;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public DZFDouble getYe() {
		return ye;
	}

	
	public String getPk_accsubj() {
		return pk_accsubj;
	}

	public void setPk_accsubj(String pk_accsubj) {
		this.pk_accsubj = pk_accsubj;
	}

	public void setYe(DZFDouble ye) {
		this.ye = ye;
	}

	public String getKmbm() {
		return kmbm;
	}

	public void setKmbm(String kmbm) {
		this.kmbm = kmbm;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String getTableName() {
		// TODO 自动生成的方法存根
		return null;
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

	public String getIsPaging() {
		return isPaging;
	}

	public void setIsPaging(String isPaging) {
		this.isPaging = isPaging;
	}

	public String getPzpk() {
		return pzpk;
	}

	public void setPzpk(String pzpk) {
		this.pzpk = pzpk;
	}
	
	

}
