package com.dzf.model.pub;

import java.util.List;

import com.dzf.model.sys.sys_power.UserVO;
import com.dzf.pub.SuperVO;
import com.dzf.pub.Field.FieldAlias;
import com.dzf.pub.lang.DZFBoolean;
import com.dzf.pub.lang.DZFDate;

/**
 * 查询条件VO
 * @author zhangj
 * 
 * 
 *
 */
public class QueryParamVO extends SuperVO {

	public QueryParamVO() {
	}
	
	@FieldAlias("corpIds")
	private String pk_corp;// 公司
	@FieldAlias("corpcode")
	private String corpcode;
	@FieldAlias("corpname")
	private String corpname;
	
	private String userid;
	
	@FieldAlias("corpIdss")
	private String[] pk_corps;//公司s
	
	private List<String> corpslist;//公司集合
	
	private UserVO uservo;//用户VO
	
	private DZFDate clientdate;//客户端日期

	@FieldAlias("qjq")
	private String qjq;// 开始期间

	@FieldAlias("qjz")
	private String qjz;// 结束期间

	private String kms_id;// 科目ID

	@FieldAlias("kms")
	private String kms; // 科目

	private String kmsx;// 科目属性

	@FieldAlias("begindate")
	private DZFDate begindate1;// 开始日期

	@FieldAlias("enddate")
	private DZFDate enddate;// 结束日期

	private DZFBoolean xswyewfs;

	private DZFBoolean xsyljfs;

	private DZFBoolean ishasjz;// 是否结帐

	private DZFBoolean ishassh;// 是否审核

	private DZFBoolean ismj;

	private String pk_currency;// 币种

	private List<String> kmcodelist;// 科目编码集合
	
	private String fathercorp;//登录公司的上级公司
	
	private String year;//年
	
	@FieldAlias("cjq")
	private Integer cjq;
	@FieldAlias("cjz")
	private Integer cjz;

	private Integer levelq;// 科目级次开始

	private Integer levelz;// 科目级次结束

	private DZFBoolean isLevel = DZFBoolean.FALSE;

	@FieldAlias("pk_assetcard")
	private String pk_assetcard;// 资产

	@FieldAlias("pk_inventory")
	private String pk_inventory;// 商品
	
	@FieldAlias("pk_assetcategory")
	private String pk_assetcategory;//资产类别
	
	@FieldAlias("ascode")
	private String ascode;//卡片编号
	
	@FieldAlias("asname")
	private String asname;//资产名称
	
	private String zcsx;//资产属性
	
	private DZFBoolean isqc;//是否期初
	
	private DZFBoolean istogl;//是否转总帐
	
	private DZFBoolean isclear;//是否清理
	
	private DZFBoolean iscarover;//仅显示已结转
	
	private DZFBoolean isuncarover;//仅显示未结转
	
	private String hc;//行次,为了现金流量用
	
	//区间查询
	private String kms_first;
	
	private String kms_last;
	
	
	public DZFBoolean getIsqc() {
		return isqc;
	}

	public void setIsqc(DZFBoolean isqc) {
		this.isqc = isqc;
	}

	public DZFBoolean getIstogl() {
		return istogl;
	}

	public void setIstogl(DZFBoolean istogl) {
		this.istogl = istogl;
	}

	public DZFBoolean getIsclear() {
		return isclear;
	}

	public void setIsclear(DZFBoolean isclear) {
		this.isclear = isclear;
	}

	public String getPk_assetcategory() {
		return pk_assetcategory;
	}

	public void setPk_assetcategory(String pk_assetcategory) {
		this.pk_assetcategory = pk_assetcategory;
	}

	public String getAscode() {
		return ascode;
	}

	public void setAscode(String ascode) {
		this.ascode = ascode;
	}

	public String getAsname() {
		return asname;
	}

	public void setAsname(String asname) {
		this.asname = asname;
	}

	public String getPk_inventory() {
		return pk_inventory;
	}

	public void setPk_inventory(String pk_inventory) {
		this.pk_inventory = pk_inventory;
	}

	public String getPk_assetcard() {
		return pk_assetcard;
	}

	public void setPk_assetcard(String pk_assetcard) {
		this.pk_assetcard = pk_assetcard;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getKms() {
		return kms;
	}

	public void setKms(String kms) {
		this.kms = kms;
	}

	public String getKmsx() {
		return kmsx;
	}

	public void setKmsx(String kmsx) {
		this.kmsx = kmsx;
	}

	public DZFDate getBegindate1() {
		return begindate1;
	}

	public void setBegindate1(DZFDate begindate1) {
		this.begindate1 = begindate1;
	}

	public DZFDate getEnddate() {
		return enddate;
	}

	public void setEnddate(DZFDate enddate) {
		this.enddate = enddate;
	}

	public DZFBoolean getXswyewfs() {
		return xswyewfs;
	}

	public void setXswyewfs(DZFBoolean xswyewfs) {
		this.xswyewfs = xswyewfs;
	}

	public DZFBoolean getXsyljfs() {
		return xsyljfs;
	}

	public void setXsyljfs(DZFBoolean xsyljfs) {
		this.xsyljfs = xsyljfs;
	}

	public DZFBoolean getIshasjz() {
		return ishasjz;
	}

	public void setIshasjz(DZFBoolean ishasjz) {
		this.ishasjz = ishasjz;
	}

	public List<String> getKmcodelist() {
		return kmcodelist;
	}

	public void setKmcodelist(List<String> kmcodelist) {
		this.kmcodelist = kmcodelist;
	}

	public DZFBoolean getIshassh() {
		return ishassh;
	}

	public void setIshassh(DZFBoolean ishassh) {
		this.ishassh = ishassh;
	}

	public String getPk_currency() {
		return pk_currency;
	}

	public void setPk_currency(String pk_currency) {
		this.pk_currency = pk_currency;
	}

	public DZFBoolean getIsmj() {
		return ismj;
	}

	public void setIsmj(DZFBoolean ismj) {
		this.ismj = ismj;
	}

	public Integer getLevelq() {
		return levelq;
	}

	public void setLevelq(Integer levelq) {
		this.levelq = levelq;
	}

	public Integer getLevelz() {
		return levelz;
	}

	public void setLevelz(Integer levelz) {
		this.levelz = levelz;
	}

	public DZFBoolean getIsLevel() {
		return isLevel;
	}

	public void setIsLevel(DZFBoolean isLevel) {
		this.isLevel = isLevel;
	}

	public String getQjq() {
		return qjq;
	}

	public void setQjq(String qjq) {
		this.qjq = qjq;
	}

	public String getQjz() {
		return qjz;
	}

	public void setQjz(String qjz) {
		this.qjz = qjz;
	}

	public Integer getCjq() {
		return cjq;
	}

	public void setCjq(Integer cjq) {
		this.cjq = cjq;
	}

	public Integer getCjz() {
		return cjz;
	}

	public void setCjz(Integer cjz) {
		this.cjz = cjz;
	}

	public String getKms_id() {
		return kms_id;
	}

	public void setKms_id(String kms_id) {
		this.kms_id = kms_id;
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return null;
	}

	public List<String> getCorpslist() {
		return corpslist;
	}

	public void setCorpslist(List<String> corpslist) {
		this.corpslist = corpslist;
	}

	public UserVO getUservo() {
		return uservo;
	}

	public void setUservo(UserVO uservo) {
		this.uservo = uservo;
	}

	public DZFDate getClientdate() {
		return clientdate;
	}

	public void setClientdate(DZFDate clientdate) {
		this.clientdate = clientdate;
	}

	public String getCorpcode() {
		return corpcode;
	}

	public void setCorpcode(String corpcode) {
		this.corpcode = corpcode;
	}

	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	public String[] getPk_corps() {
		return pk_corps;
	}

	public void setPk_corps(String[] pk_corps) {
		this.pk_corps = pk_corps;
	}

	public String getKms_first() {
		return kms_first;
	}

	public void setKms_first(String kms_first) {
		this.kms_first = kms_first;
	}

	public String getKms_last() {
		return kms_last;
	}

	public void setKms_last(String kms_last) {
		this.kms_last = kms_last;
	}

	public String getFathercorp() {
		return fathercorp;
	}

	public void setFathercorp(String fathercorp) {
		this.fathercorp = fathercorp;
	}

	public String getZcsx() {
		return zcsx;
	}

	public void setZcsx(String zcsx) {
		this.zcsx = zcsx;
	}

	public DZFBoolean getIscarover() {
		return iscarover;
	}

	public void setIscarover(DZFBoolean iscarover) {
		this.iscarover = iscarover;
	}

	public DZFBoolean getIsuncarover() {
		return isuncarover;
	}

	public void setIsuncarover(DZFBoolean isuncarover) {
		this.isuncarover = isuncarover;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getHc() {
		return hc;
	}

	public void setHc(String hc) {
		this.hc = hc;
	}
}
