package com.dzf.model.pub;

import java.util.Date;

public class SearchPara {
	private Integer cxType;    //0、按期间查询 1、按时间查询
	private String startQj_year;    //开始期间
	private String endQj_year;      //结束期间
	private String startQj_month;    //开始期间
	private String endQj_month;      //结束期间
	private Integer shuxing;   //属性
	private Integer jici;      //级次
	private String bizhong;    //币种
	private String showType;   //显示类型
	private String corpIds;   //公司id
	private String startkm;    //开始科目
	private String endkm;      //结束科目
	private Date startDate;    //开始时间
	private Date endDate;      //结束时间
	public String getStartkm() {
		return startkm;
	}
	public void setStartkm(String startkm) {
		this.startkm = startkm;
	}
	public String getEndkm() {
		return endkm;
	}
	public void setEndkm(String endkm) {
		this.endkm = endkm;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getCxType() {
		return cxType;
	}
	public void setCxType(Integer cxType) {
		this.cxType = cxType;
	}
	public Integer getShuxing() {
		return shuxing;
	}
	public void setShuxing(Integer shuxing) {
		this.shuxing = shuxing;
	}
	public Integer getJici() {
		return jici;
	}
	public void setJici(Integer jici) {
		this.jici = jici;
	}
	public String getBizhong() {
		return bizhong;
	}
	public void setBizhong(String bizhong) {
		this.bizhong = bizhong;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public String getCorpIds() {
		return corpIds;
	}
	public void setCorpIds(String corpIds) {
		this.corpIds = corpIds;
	}
	public String getStartQj_year() {
		return startQj_year;
	}
	public void setStartQj_year(String startQj_year) {
		this.startQj_year = startQj_year;
	}
	public String getEndQj_year() {
		return endQj_year;
	}
	public void setEndQj_year(String endQj_year) {
		this.endQj_year = endQj_year;
	}
	public String getStartQj_month() {
		return startQj_month;
	}
	public void setStartQj_month(String startQj_month) {
		this.startQj_month = startQj_month;
	}
	public String getEndQj_month() {
		return endQj_month;
	}
	public void setEndQj_month(String endQj_month) {
		this.endQj_month = endQj_month;
	}
}
