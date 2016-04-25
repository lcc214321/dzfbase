package com.dzf.model.sys.sys_power;

import com.dzf.pub.SuperVO;
import com.dzf.pub.Field.FieldAlias;
import com.dzf.pub.lang.DZFDate;
import com.dzf.pub.lang.DZFDateTime;

/**
 * 
 * 证件信息
 *
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class CorpCredenVO extends SuperVO {

	@FieldAlias("credenid")
	private String pk_credential; // 主键

	@FieldAlias("corp_id")
	private String pk_corp; // 客户主键
	
	@FieldAlias("kname")
	private String corpkname; //公司名称

	@FieldAlias("vcname")
	private String vcredname; // 证件名称

	@FieldAlias("vcode")
	private String vcredcode; // 证件编号

	@FieldAlias("dexpdate")
	private DZFDate dexpiredate; // 证件到期日

	@FieldAlias("expday")
	private Integer iexpireday; // 提前几天提醒

	@FieldAlias("perid")
	private String vpersionid;// 提醒对象

	private Integer dr; // 删除标记

	private DZFDateTime ts; // 时间
	

	public String getCorpkname() {
		return corpkname;
	}

	public void setCorpkname(String corpkname) {
		this.corpkname = corpkname;
	}

	public String getPk_credential() {
		return pk_credential;
	}

	public void setPk_credential(String pk_credential) {
		this.pk_credential = pk_credential;
	}

	public String getVcredname() {
		return vcredname;
	}

	public void setVcredname(String vcredname) {
		this.vcredname = vcredname;
	}

	public String getVcredcode() {
		return vcredcode;
	}

	public void setVcredcode(String vcredcode) {
		this.vcredcode = vcredcode;
	}

	public DZFDate getDexpiredate() {
		return dexpiredate;
	}

	public void setDexpiredate(DZFDate dexpiredate) {
		this.dexpiredate = dexpiredate;
	}

	public Integer getIexpireday() {
		return iexpireday;
	}

	public void setIexpireday(Integer iexpireday) {
		this.iexpireday = iexpireday;
	}

	public String getVpersionid() {
		return vpersionid;
	}

	public void setVpersionid(String vpersionid) {
		this.vpersionid = vpersionid;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public DZFDateTime getTs() {
		return ts;
	}

	public void setTs(DZFDateTime ts) {
		this.ts = ts;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	@Override
	public String getParentPKFieldName() {
		return "pk_corp";
	}

	@Override
	public String getPKFieldName() {
		return "pk_credential";
	}

	@Override
	public String getTableName() {
		return "ynt_credential";
	}
}
