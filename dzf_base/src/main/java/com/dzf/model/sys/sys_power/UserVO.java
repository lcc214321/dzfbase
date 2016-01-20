package com.dzf.model.sys.sys_power;

import com.dzf.pub.SuperVO;
import com.dzf.pub.Field.FieldAlias;
import com.dzf.pub.Field.FieldValidate;
import com.dzf.pub.lang.DZFBoolean;
import com.dzf.pub.lang.DZFDate;
import com.dzf.pub.lang.DZFDateTime;

/**
 * 存储用户（操作员）信息的VO类。
 * 
 */
public class UserVO extends SuperVO {
	
	 
	@FieldAlias("rname")
	private String realName;
	@FieldAlias("phonenum")
	private String phone;
	@FieldAlias("ck_code")
	@FieldValidate("验证码不能为空:checkcode is not null;")
	private String checkcode;
	private String langcode;
	@FieldAlias("pdl")
	private String pwdlevelcode;
	@FieldAlias("crtcorp_id")
	private String pk_creatcorp;
	@FieldAlias("att")
	private String authen_type;
	@FieldAlias("bl1")
	private DZFBoolean isca;
	@FieldAlias("lock_flag")
	private DZFBoolean locked_tag;
	@FieldAlias("ucode")
	@FieldValidate("用户编码不能为空:user_code is not null;")
	private String user_code;
	@FieldAlias("en_time")
	private DZFDate able_time;
	@FieldAlias("dis_time")
	private DZFDate disable_time;
	private DZFDateTime ts;
	@FieldAlias("u_pwd")
//	@FieldValidate("密码不能为空:user_password is not null;")
	private String user_password;
	@FieldAlias("yyyyy")
	private DZFBoolean bappuser;
	@FieldAlias("b_mng")
	private DZFBoolean ismanager;
	@FieldAlias("u_note")
	private String user_note;
	@FieldAlias("corp_id")
	private String pk_corp;
	@FieldAlias("zzzz")
	private String pwdparam;
	@FieldAlias("uname")
	@FieldValidate("用户名称不能为空:user_name is not null;")
	private String user_name;
	private Integer dr;
	private Integer istate;
	@FieldAlias("gjyh")
	private DZFBoolean keyuser;
	@FieldAlias("uid")
	private String cuserid;
	@FieldAlias("pppp")
	private int pwdtype;
	//手机用户注册未签约公司 保存临时公司主键
	private String pk_tempcorp;
	//签约时回写签约公司主键
	private String pk_signcorp;
	//是否可图片处理
	private DZFBoolean bdata;
	//是否财务处理
	private DZFBoolean baccount;

	private String corpnm;
	private String crtcorp;
	
	@FieldAlias("QQ")
	private String user_qq;
	@FieldAlias("mail")
	private String user_mail;
	
	
	
	
	
	public String getUser_qq() {
		return user_qq;
	}
	public void setUser_qq(String user_qq) {
		this.user_qq = user_qq;
	}
	public String getUser_mail() {
		return user_mail;
	}
	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}
	public String getCorpnm() {
		return corpnm;
	}
	public void setCorpnm(String corpnm) {
		this.corpnm = corpnm;
	}
	public String getCrtcorp() {
		return crtcorp;
	}
	public void setCrtcorp(String crtcorp) {
		this.crtcorp = crtcorp;
	}
	public DZFBoolean getBdata() {
		return bdata;
	}
	public void setBdata(DZFBoolean bdata) {
		this.bdata = bdata;
	}
	public DZFBoolean getBaccount() {
		return baccount;
	}
	public void setBaccount(DZFBoolean baccount) {
		this.baccount = baccount;
	}
	public String getPk_tempcorp() {
		return pk_tempcorp;
	}
	public void setPk_tempcorp(String pk_tempcorp) {
		this.pk_tempcorp = pk_tempcorp;
	}
	public String getPk_signcorp() {
		return pk_signcorp;
	}
	public void setPk_signcorp(String pk_signcorp) {
		this.pk_signcorp = pk_signcorp;
	}
	public String getCheckcode() {
		return checkcode;
	}
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	public String getLangcode() {
		return langcode;
	}
	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}
	public String getPwdlevelcode() {
		return pwdlevelcode;
	}
	public void setPwdlevelcode(String pwdlevelcode) {
		this.pwdlevelcode = pwdlevelcode;
	}
	public String getPk_creatcorp() {
		return pk_creatcorp;
	}
	public void setPk_creatcorp(String pk_creatcorp) {
		this.pk_creatcorp = pk_creatcorp;
	}
	public String getAuthen_type() {
		return authen_type;
	}
	public void setAuthen_type(String authen_type) {
		this.authen_type = authen_type;
	}
	public DZFBoolean getIsca() {
		return isca;
	}
	public void setIsca(DZFBoolean isca) {
		this.isca = isca;
	}
	public DZFBoolean getLocked_tag() {
		return locked_tag;
	}
	public void setLocked_tag(DZFBoolean locked_tag) {
		this.locked_tag = locked_tag;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public DZFDate getAble_time() {
		return able_time;
	}
	public void setAble_time(DZFDate able_time) {
		this.able_time = able_time;
	}
	public DZFDate getDisable_time() {
		return disable_time;
	}
	public void setDisable_time(DZFDate disable_time) {
		this.disable_time = disable_time;
	}
	public DZFDateTime getTs() {
		return ts;
	}
	public void setTs(DZFDateTime ts) {
		this.ts = ts;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public DZFBoolean getBappuser() {
		return bappuser;
	}
	public void setBappuser(DZFBoolean bappuser) {
		this.bappuser = bappuser;
	}
	public DZFBoolean getIsmanager() {
		return ismanager;
	}
	public void setIsmanager(DZFBoolean ismanager) {
		this.ismanager = ismanager;
	}
	public String getUser_note() {
		return user_note;
	}
	public void setUser_note(String user_note) {
		this.user_note = user_note;
	}
	public String getPk_corp() {
		return pk_corp;
	}
	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}
	public String getPwdparam() {
		return pwdparam;
	}
	public void setPwdparam(String pwdparam) {
		this.pwdparam = pwdparam;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Integer getDr() {
		return dr;
	}
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	public Integer getIstate() {
		return istate;
	}
	public void setIstate(Integer istate) {
		this.istate = istate;
	}
	public DZFBoolean getKeyuser() {
		return keyuser;
	}
	public void setKeyuser(DZFBoolean keyuser) {
		this.keyuser = keyuser;
	}
	public String getCuserid() {
		return cuserid;
	}
	public void setCuserid(String cuserid) {
		this.cuserid = cuserid;
	}
	public int getPwdtype() {
		return pwdtype;
	}
	public void setPwdtype(int pwdtype) {
		this.pwdtype = pwdtype;
	}
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String getPKFieldName() {
		return "cuserid";
	}
	@Override
	public String getTableName() {
		return "sm_user";
	}
	@Override
	public String getParentPKFieldName() {
		return null;
	}
	
}