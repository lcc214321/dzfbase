package com.dzf.model.sys.sys_power;

import com.dzf.pub.SuperVO;
import com.dzf.pub.Field.FieldAlias;
import com.dzf.pub.lang.DZFBoolean;
import com.dzf.pub.lang.DZFDate;
import com.dzf.pub.lang.DZFDateTime;
import com.dzf.pub.lang.DZFDouble;

public class CorpVO<T extends SuperVO<T>> extends SuperVO {
    private static final long serialVersionUID = -7255675917825048433L;

    @FieldAlias("bdate")
    public DZFDate begindate;// (建账日期)

    @FieldAlias("bintro")
    public String briefintro;// 客户简介

    @FieldAlias("chcode")
    public String chargedeptcode;

    @FieldAlias("chname")
    public String chargedeptname;// 公司性质

    @FieldAlias("ccounty")
    public String citycounty;

    @FieldAlias("ctype")
    public String corptype;// 科目方案

    @FieldAlias("carea")
    public String countryarea;

    @FieldAlias("icbegindate")
    public DZFDate icbegindate;// 库存启用日期

    @FieldAlias("cdate")
    public DZFDate createdate;// 录入日期

    @FieldAlias("d1")
    public String def1;// 服务机构
    // AppSecret
    @FieldAlias("d10")
    public String def10;// ---------经度----------------如果小企业的话，存的是微报销的随机id.

    @FieldAlias("d11")
    public String def11;// ----------维度----------------如果小企业的话，存的是激活码

    @FieldAlias("d12")
    public String def12;// ---------王钊宁---是否已票通认证完成，如果是代账公司，存企业识别号

    @FieldAlias("d13")
    public String def13;// -----------小薇无优网站，服务主体

    @FieldAlias("d14")
    public String def14;// ------------大账房服务商合同协议[贺智鹏]

    @FieldAlias("d15")
    public String def15;// ------------证件类型[贺智鹏]

    @FieldAlias("d16")
    public String def16;// ----------- 登录方式 CFCA证书和法人一证通[贺智鹏]

    @FieldAlias("d17")
    public String def17;// -----------停止服务原因[贺智鹏]

    @FieldAlias("d18")
    public String def18;// -----------最后修改人[贺智鹏]

    @FieldAlias("d19")
    public String def19;// ----------税负预警，null 为1%

    @FieldAlias("d2")
    public String def2;// 服务热线

    @FieldAlias("d20")
    public String def20;

    @FieldAlias("d3")
    public String def3;// 联系方式

    @FieldAlias("d4")
    public String def4;//// 图片处理流程id

    public DZFBoolean isneedocr;// 是否智能识别，供展示用
    public DZFBoolean isneedappro;// 是否需要审核，供展示用
    public String djshfs;// 审核方式，供展示用

    @FieldAlias("d5")
    public String def5;///// 临时公司id

    @FieldAlias("d6")
    public String def6;/////////////// ------------------------------【我的客户，服务信息里】，客户经理id，取自sm_user表，配对使用

    @FieldAlias("d7")
    public String def7;/////////////// --------------------------------【我的客户，服务信息里】，客户经理名称，取自sm_user表，配对使用

    @FieldAlias("d8")
    public String def8;////////////// ----------授权企业主打印导出 。如果是代账公司，存官方网址

    @FieldAlias("d9")
    public String def9;///////////// -----------------------------------注册资本
    // public Integer ecotype;

    @FieldAlias("ectype")
    public String ecotype;

    @FieldAlias("e1")
    public String email1;// 电子邮件

    @FieldAlias("e2")
    public String email2;

    @FieldAlias("e3")
    public String email3;

    @FieldAlias("edate")
    public DZFDate enddate;

    @FieldAlias("fcorp")
    public String fathercorp;

    @FieldAlias("f1")
    public String fax1;

    @FieldAlias("f2")
    public String fax2;

    @FieldAlias("fname")
    public String foreignname;// 销售代表

    @FieldAlias("fid") // ---------使用他当代理商id，存代理商档案的id
    public String foreignid;

    public String dlsname;// 代理商名称，不存库

    @FieldAlias("hflag")
    public DZFBoolean holdflag;// 是否启用资产

    @FieldAlias("idnum")
    public String idnumber;

    @FieldAlias("indus")
    public String industry;// 行业

    @FieldAlias("incode")
    public String innercode;

    @FieldAlias("hasaccount")
    public DZFBoolean ishasaccount;// 是否已建帐

    @FieldAlias("seal")
    public DZFBoolean isseal;// 是否已停用

    @FieldAlias("workingunit")
    public DZFBoolean isworkingunit;

    @FieldAlias("bodycode")
    public String legalbodycode;// 法人代表

    @FieldAlias("l1")
    public String linkman1;// 财务负责人

    @FieldAlias("l2")
    public String linkman2;// 客户联系人

    @FieldAlias("l3")
    public String linkman3;// 微信号

    @FieldAlias("backup")
    public boolean m_isbackup;

    @FieldAlias("mcode")
    public String maxinnercode;

    @FieldAlias("memo")
    public String memo;

    @FieldAlias("ownerrate")
    public DZFDouble ownersharerate;

    @FieldAlias("p1")
    public String phone1;// 联系人电话

    @FieldAlias("p2")
    public String phone2;// 验证码电话

    @FieldAlias("p3")
    public String phone3;

    @FieldAlias("pk_gs")
    public String pk_corp;

    @FieldAlias("pk_gskind")
    public String pk_corpkind;

    @FieldAlias("pk_cur")
    public String pk_currency;

    @FieldAlias("postadd")
    public String postaddr;// 单位地址

    @FieldAlias("province")
    public String province;

    @FieldAlias("regcap")
    public DZFDouble regcapital;

    @FieldAlias("region")
    private String region;

    @FieldAlias("saleaddr")
    public String saleaddr;

    @FieldAlias("sealeddate")
    public DZFDate sealeddate;// 停止服务日期

    @FieldAlias("payertype")
    private Integer taxpayertype; // 纳税人类别

    @FieldAlias("ts")
    private DZFDateTime ts;

    @FieldAlias("ucode")
    public String unitcode;// 公司编码

    @FieldAlias("uinction")
    private String unitdistinction;

    @FieldAlias("uname")
    public String unitname;// 公司名称

    @FieldAlias("ushortname")
    public String unitshortname;

    @FieldAlias("url")
    public String url;

    @FieldAlias("zcode")
    public String zipcode;

    @FieldAlias("s_order")
    public Integer showorder;

    @FieldAlias("settlecenter")
    public DZFBoolean issettlecenter;

    @FieldAlias("bb_date")
    private DZFDate busibegindate; // 固定资产启用日期

    @FieldAlias("bd_date")
    private DZFDate busienddate; // HR业务结束日期

    @FieldAlias("tax_code")
    public String taxcode;; // --公司类别(普通,结算中心)-- 税务代码

    @FieldAlias("accountcorp")
    private DZFBoolean isaccountcorp;// 是否会计公司

    @FieldAlias("datacorp")
    private DZFBoolean isdatacorp;// 是否数据中心

    @FieldAlias("curr")
    private DZFBoolean iscurr;// 是否多币种

    @FieldAlias("costforwardstyle")
    private Integer icostforwardstyle;// 成本结转类型

    @FieldAlias("buildic")
    private DZFBoolean bbuildic;// --启用ic模块-- 是否库存管理

    @FieldAlias("buildicstyle")
    private Integer ibuildicstyle;// 存货核算类型--针对启用进销存[ 0或者空为老模式库存。 ] [ 1为新模式库存。 ]

    @FieldAlias("useretail")
    public DZFBoolean isuseretail; // 是否用于零售

    @FieldAlias("indusname")
    public String indusname;// 行业名称

    @FieldAlias("ctypename")
    public String ctypename;// 行业科目方案

    @FieldAlias("croparea")
    public String cropArea;// 行业科目名称

    @FieldAlias("issmall")
    private DZFBoolean issmall;// 是否小型微利企业[做纳税申报使用]

    @FieldAlias("establishtime")
    private String establishtime;// 成立时间

    /** 公司属性 : (add by liuxing) */
    @FieldAlias("companyproperty")
    public Integer companyproperty;
    /** 会计公司ID */
    @FieldAlias("accountfactoryid")
    public String accountfactoryid;
    /**
     * 新版管理平台新增字段
     */
    @FieldAlias("rporationid")
    public String vcorporationid;// 法人身份证号
    @FieldAlias("rsonal")
    public DZFBoolean ispersonal;// 个人用户
    @FieldAlias("gcode")
    public String vorgcode;// 组织机构代码
    @FieldAlias("silicode")
    public String vbusilicode;// 营业执照号
    @FieldAlias("cexpdate")
    public DZFDate dlicexpdate;// 执照到期日
    @FieldAlias("countopen")
    public String vaccountopen;// 开户许可证
    @FieldAlias("mpcode")
    public String vcompcode;// 计算机代码
    @FieldAlias("mpdate")
    public DZFDate dcompdate;// 代码到期日
    @FieldAlias("ccrecode")
    public String vsoccrecode;// 社会信用代码
    @FieldAlias("dsdate")
    private DZFDate dscodedate;// 信用代码到期日
    @FieldAlias("nkname")
    public String vbankname;// 开户银行
    @FieldAlias("nkcode")
    public String vbankcode;// 账号
    @FieldAlias("nkaddr")
    public String vbankaddr;// 银行地址
    @FieldAlias("nkpos")
    public String vbankpos;// 银行位置
    @FieldAlias("sitype")
    public String vbusitype;// 业务类型
    @FieldAlias("lecode")
    public String vfilecode;// 档案号
    @FieldAlias("ovince")
    public Integer vprovince;// 省
    @FieldAlias("city")
    public Integer vcity;// 市
    @FieldAlias("area")
    public Integer varea;// 区
    @FieldAlias("stsource")
    public String vcustsource;// 客户来源
    @FieldAlias("urcenote")
    public String vsourcenote;// 客户来源说明
    @FieldAlias("stmainbusi")
    public String vcustmainbusi;// 客户主营业务
    @FieldAlias("spaccount")
    public String vrespaccount;// 负责会计
    @FieldAlias("sptel")
    public String vresptel;// 负责会计电话
    @FieldAlias("stothertel")
    public String vcustothertel;// 客户其他联系方式
    @FieldAlias("atetaxplace")
    public String vstatetaxplace;// 国税主管所
    @FieldAlias("atetaxaddr")
    public String vstatetaxaddr;// 国税位置
    @FieldAlias("atetaxper")
    public String vstatetaxper;// 国税专管员
    @FieldAlias("atetaxpertel")
    public String vstatetaxpertel;// 国税专管员电话
    @FieldAlias("atetaxpwd")
    public String vstatetaxpwd;// 国税密码
    @FieldAlias("atetaxdate")
    public DZFDate dstatetaxdate;// 有效期至
    @FieldAlias("caltaxcode")
    public String vlocaltaxcode;// 地税登记号
    @FieldAlias("caltaxplace")
    public String vlocaltaxplace;// 地税主管所
    @FieldAlias("caltaxper")
    public String vlocaltaxper;// 地税专管员
    @FieldAlias("caltaxpertel")
    public String vlocaltaxpertel;// 地税专管员电话
    @FieldAlias("caltaxaddr")
    public String vlocaltaxaddr;// 地税位置
    @FieldAlias("caltaxpwd")
    public String vlocaltaxpwd;// 地税密码
    @FieldAlias("vperpwd")
    public String vpersonalpwd;// 个税密码

    @FieldAlias("rporatetax")
    public Integer vcorporatetax;// 企业所得税
    @FieldAlias("xtype")
    public DZFBoolean vtaxtype;// 税控类型
    @FieldAlias("xcode")
    public String vtaxcode;// 税务登记证号

    @FieldAlias("nvohtype")
    public String vgenvohtype;// 图片生成凭证方式
    @FieldAlias("pcount")
    public String pcountname;// 主管会计名称
    @FieldAlias("pcountid")
    public String vsuperaccount;// 主管会计主键
    @FieldAlias("wqcount")
    public String wqcountname;// 外勤会计名称
    @FieldAlias("wqcountid")
    public String vwqaccount;// 外勤会计主键
    @FieldAlias("dcldate")
    public DZFDate destablishdate;// 成立日期
    @FieldAlias("ukey")
    public DZFBoolean isukey;// 地税有无UKEY
    @FieldAlias("dudate")
    private DZFDate dukeydate;// UKEY到期日
    @FieldAlias("sourceid")
    private String pk_source;// 来源主键

    private String text;

    @FieldAlias("pyfc")
    private String pyfirstcomb;// 客户名称首字母(王钊宁的客户模糊查询)

    private String accountcoderule;

    // 益世税务申报添加字段 WJX
    @FieldAlias("sbfs")
    private Integer taxreporttype;// 申报方式 WJX 申报方式:0:按月申报，1:按季申报
    @FieldAlias("zsfs")
    private Integer taxlevytype;// 征收方式 WJX 征收方式:0:定期定额征收（核定征收），1:查账征收
    @FieldAlias("jspbh")
    private String golddiskno;// 金税盘编号 WJX
    @FieldAlias("ssyhed")
    private DZFDouble taxfeeamount;// 税收优惠额度 WJX

    //
    @FieldAlias("capital")
    public DZFDouble registcapital;// 注册资本

    @FieldAlias("kwxjr")
    private DZFBoolean iskwxjr;// 申请科委小巨人基金

    @FieldAlias("books")
    private String accbooks;// 对应核算账簿

    @FieldAlias("booksname")
    private String booksname;// 对应核算账簿名称

    @FieldAlias("cbtax")
    private DZFDouble citybuildtax; // 城建税

    @FieldAlias("ismantax")
    private DZFBoolean ismaintainedtax;// 是否已维护税率信息

    @FieldAlias("lmtime")
    private DZFDateTime lastmodifytime; // 最后修改时间

    @FieldAlias("induscode")
    private String industrycode; // 一键报税行业代码

    @FieldAlias("taxconmatpe")
    private String taxcontrmachtype; // 税控器具类型

    @FieldAlias("dkfp")
    private DZFBoolean isdkfp;// 代开发票

    @FieldAlias("dbbx")
    private DZFBoolean isdbbx;// 代办保险

    @FieldAlias("sxrq")
    private Integer isxrq;// 一般纳税人生效日期

    @FieldAlias("rdsj")
    private DZFDate drdsj;// 认定时间

    @FieldAlias("ywskp")
    private DZFBoolean isywskp;// 有无税控盘

    @FieldAlias("fwgs")
    private Integer ifwgs;// 房屋归属

    @FieldAlias("sdsbsjg")
    private Integer isdsbsjg;// 所得税报送机关(0-国税局、1-地税局)

    @FieldAlias("kjzc")
    private Integer ikjzc;// 会计政策(0-企业会计准则、1-小企业会计准则、2-企业会计制度、3-事业单位会计准则、4-民间非营利组织会计制度)

    @FieldAlias("rzsj")
    private DZFDate drzsj;// 认证时间

    @FieldAlias("schlnd")
    private String vschlnd;// 首次获利年度

    @FieldAlias("yhzc")
    private String vyhzc;// 公司可享受的优惠政策

    private String accountProgress;// 做账进度

    @FieldAlias("bday")
    public Integer rembday;// 提醒开始日期

    @FieldAlias("eday")
    public Integer remeday;// 提醒截止日期

    @FieldAlias("beday")
    public String rembeday;// 提醒开始-截止日期

    @FieldAlias("formal")
    private DZFBoolean isformal;// 是否正式客户

    private T[] corpRoleVos;// 角色信息

    private T[] corpCredenVos;// 证件信息

    private T[] corpDocVos;// 附件信息

    private T[] corpTaxInfoVos;// 税率信息
    
    private T[] corpSholderVos;// 股东信息

    @FieldAlias("taxercode")
    private String vtaxofficercode;// 人员编码

    @FieldAlias("taxerid")
    private String vtaxofficer;// 办税人员id

    @FieldAlias("taxer")
    private String vtaxofficernm;// 办税人员名称

    @FieldAlias("corprhone")
    private String vcorporatephone;// 法人电话

    @FieldAlias("comptype")
    private Integer icompanytype;// 公司类型 1：有限公司；2：个人独资企业；3：合伙企业；

    @FieldAlias("buscope")
    private String vbusinescope;// 经营范围

    @FieldAlias("approdate")
    private DZFDate dapprovaldate;// 核准日期（发证日期）

    @FieldAlias("regans")
    private String vregistorgans;// 登记机关

    @FieldAlias("tradecode")
    private String vtradecode;// 国家标准行业编码

    private DZFBoolean ischannel;
    private Integer approve_status;
    private String approve_comment;
    private String approve_user;
    private String approve_user_name;
    private DZFDateTime approve_time;

    @FieldAlias("isfactory")
	public DZFBoolean isfactory;//是否会计工厂
    
    @FieldAlias("copid")
    private String coperatorid; // 录入人
    
    @FieldAlias("statusnm")
    private String statusname;//客户状态
    
    private Integer custtype;//客户类型
    
    private String accountProgressDate;//彬杰，默认登录日期 调整 默认做账进度年月
    
	public Integer getCusttype() {
		return custtype;
	}

	public void setCusttype(Integer custtype) {
		this.custtype = custtype;
	}

	public String getCoperatorid() {
        return coperatorid;
    }

    public void setCoperatorid(String coperatorid) {
        this.coperatorid = coperatorid;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public DZFBoolean getIsfactory() {
		return isfactory;
	}

	public void setIsfactory(DZFBoolean isfactory) {
		this.isfactory = isfactory;
	}
	
    public DZFBoolean getIschannel() {
        return ischannel;
    }

    public void setIschannel(DZFBoolean ischannel) {
        this.ischannel = ischannel;
    }

    public Integer getApprove_status() {
        return approve_status;
    }

    public void setApprove_status(Integer approve_status) {
        this.approve_status = approve_status;
    }

    public String getApprove_comment() {
        return approve_comment;
    }

    public void setApprove_comment(String approve_comment) {
        this.approve_comment = approve_comment;
    }

    public String getApprove_user() {
        return approve_user;
    }

    public void setApprove_user(String approve_user) {
        this.approve_user = approve_user;
    }

    public String getApprove_user_name() {
        return approve_user_name;
    }

    public void setApprove_user_name(String approve_user_name) {
        this.approve_user_name = approve_user_name;
    }

    public DZFDateTime getApprove_time() {
        return approve_time;
    }

    public void setApprove_time(DZFDateTime approve_time) {
        this.approve_time = approve_time;
    }

    public String getVtaxofficercode() {
        return vtaxofficercode;
    }

    public void setVtaxofficercode(String vtaxofficercode) {
        this.vtaxofficercode = vtaxofficercode;
    }

    public String getVtaxofficer() {
        return vtaxofficer;
    }

    public void setVtaxofficer(String vtaxofficer) {
        this.vtaxofficer = vtaxofficer;
    }

    public String getVtaxofficernm() {
        return vtaxofficernm;
    }

    public void setVtaxofficernm(String vtaxofficernm) {
        this.vtaxofficernm = vtaxofficernm;
    }

    public String getVcorporatephone() {
        return vcorporatephone;
    }

    public void setVcorporatephone(String vcorporatephone) {
        this.vcorporatephone = vcorporatephone;
    }

    public Integer getIcompanytype() {
        return icompanytype;
    }

    public void setIcompanytype(Integer icompanytype) {
        this.icompanytype = icompanytype;
    }

    public String getVbusinescope() {
        return vbusinescope;
    }

    public void setVbusinescope(String vbusinescope) {
        this.vbusinescope = vbusinescope;
    }

    public DZFDate getDapprovaldate() {
        return dapprovaldate;
    }

    public void setDapprovaldate(DZFDate dapprovaldate) {
        this.dapprovaldate = dapprovaldate;
    }

    public String getVregistorgans() {
        return vregistorgans;
    }

    public void setVregistorgans(String vregistorgans) {
        this.vregistorgans = vregistorgans;
    }

    public String getVtradecode() {
        return vtradecode;
    }

    public void setVtradecode(String vtradecode) {
        this.vtradecode = vtradecode;
    }

    /**
     * 使用主键字段进行初始化的构造子。
     * 
     * 创建日期：(2001-5-16)
     */
    public CorpVO() {
    }

    public DZFBoolean getIsformal() {
        return isformal;
    }

    public void setIsformal(DZFBoolean isformal) {
        this.isformal = isformal;
    }

    public Integer getRembday() {
        return rembday;
    }

    public void setRembday(Integer rembday) {
        this.rembday = rembday;
    }

    public Integer getRemeday() {
        return remeday;
    }

    public void setRemeday(Integer remeday) {
        this.remeday = remeday;
    }

    public String getRembeday() {
        return rembeday;
    }

    public void setRembeday(String rembeday) {
        this.rembeday = rembeday;
    }

    public Integer getTaxreporttype() {
        return taxreporttype;
    }

    public void setTaxreporttype(Integer taxreporttype) {
        this.taxreporttype = taxreporttype;
    }

    public Integer getTaxlevytype() {
        return taxlevytype;
    }

    public void setTaxlevytype(Integer taxlevytype) {
        this.taxlevytype = taxlevytype;
    }

    public String getGolddiskno() {
        return golddiskno;
    }

    public void setGolddiskno(String golddiskno) {
        this.golddiskno = golddiskno;
    }

    public DZFDouble getTaxfeeamount() {
        return taxfeeamount;
    }

    public void setTaxfeeamount(DZFDouble taxfeeamount) {
        this.taxfeeamount = taxfeeamount;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getCompanyproperty() {
        return companyproperty;
    }

    public void setCompanyproperty(Integer companyproperty) {
        this.companyproperty = companyproperty;
    }

    public String getAccountfactoryid() {
        return accountfactoryid;
    }

    public void setAccountfactoryid(String accountfactoryid) {
        this.accountfactoryid = accountfactoryid;
    }

    /**
     * 使用主键进行初始化的构造子。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param ??fieldNameForMethod??
     *            主键值
     */
    public CorpVO(String newPk_corp) {

        // 为主键字段赋值:
        pk_corp = newPk_corp;

    }

    /**
     * -------------------------------------------------- 功能：
     * 
     * 
     * 输入：
     * 
     * 输出：
     * 
     * 异常：
     * 
     * 补充：
     * 
     * 
     * 创建日期：(2003-10-31 8:57:13)
     * --------------------------------------------------
     * 
     * @return DZFDate
     */
    public DZFDate getBegindate() {
        return begindate;
    }

    /**
     * 此处插入方法说明。 创建日期：(02-5-23 13:51:19)
     * 
     * @return String
     */
    public String getBriefintro() {
        return briefintro;
    }

    /**
     * -------------------------------------------------- 功能：
     * 
     * 
     * 输入：
     * 
     * 输出：
     * 
     * 异常：
     * 
     * 补充：
     * 
     * 
     * 创建日期：(2003-10-31 8:57:13)
     * --------------------------------------------------
     * 
     * @return String
     */
    public String getChargedeptcode() {
        return chargedeptcode;
    }

    /**
     * -------------------------------------------------- 功能：
     * 
     * 
     * 输入：
     * 
     * 输出：
     * 
     * 异常：
     * 
     * 补充：
     * 
     * 
     * 创建日期：(2003-10-31 8:57:13)
     * --------------------------------------------------
     * 
     * @return String
     */
    public String getChargedeptname() {
        return chargedeptname;
    }

    /**
     * 此处插入方法说明。 创建日期：(02-5-23 13:51:19)
     * 
     * @return String
     */
    public String getCitycounty() {
        return citycounty;
    }

    /**
     * 此处插入方法说明。 创建日期：(2003-5-20 14:11:57)
     * 
     * @return String
     */
    public String getCorptype() {
        return corptype;
    }

    /**
     * 此处插入方法说明。 创建日期：(02-5-23 13:51:19)
     * 
     * @return String
     */
    public String getCountryarea() {
        return countryarea;
    }

    /**
     * 此处插入方法说明。 创建日期：(02-4-18 16:05:35)
     * 
     * @return DZFDate
     */
    public DZFDate getCreatedate() {
        return createdate;
    }

    /**
     * issettlecenter的getter
     * 
     * @return DZFBoolean
     */
    public DZFBoolean getIssettlecenter() {
        return issettlecenter;
    }

    /**
     * 属性def1的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef1() {
        return def1;
    }

    /**
     * 属性def10的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef10() {
        return def10;
    }

    /**
     * 属性def11的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef11() {
        return def11;
    }

    /**
     * 属性def12的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef12() {
        return def12;
    }

    /**
     * 属性def13的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef13() {
        return def13;
    }

    /**
     * 属性def14的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef14() {
        return def14;
    }

    /**
     * 属性def15的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef15() {
        return def15;
    }

    /**
     * 属性def16的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef16() {
        return def16;
    }

    /**
     * 属性def17的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef17() {
        return def17;
    }

    /**
     * 属性def18的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef18() {
        return def18;
    }

    /**
     * 属性def19的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef19() {
        return def19;
    }

    /**
     * 属性def2的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef2() {
        return def2;
    }

    /**
     * 属性def20的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef20() {
        return def20;
    }

    /**
     * 属性def3的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef3() {
        return def3;
    }

    /**
     * 属性def4的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef4() {
        return def4;
    }

    /**
     * 属性def5的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef5() {
        return def5;
    }

    /**
     * 属性def6的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef6() {
        return def6;
    }

    /**
     * 属性def7的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef7() {
        return def7;
    }

    /**
     * 属性def8的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef8() {
        return def8;
    }

    /**
     * 属性def9的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getDef9() {
        return def9;
    }

    /**
     * 获得vo里第index个自定义项的值。 典型实现: switch(index){ case 1: return getDef1(); case
     * 2: return getDef2(); case 3: return getDef3(); ... } return null;
     * 
     * 创建日期：(01-5-14 10:41:57)
     * 
     * @return String
     * @param index
     *            int
     */
    public String getDefValue(int index) {
        switch (index) {
        case 1:
            return getDef1();
        case 2:
            return getDef2();
        case 3:
            return getDef3();
        case 4:
            return getDef4();
        case 5:
            return getDef5();
        case 6:
            return getDef6();
        case 7:
            return getDef7();
        case 8:
            return getDef8();
        case 9:
            return getDef9();
        case 10:
            return getDef10();
        case 11:
            return getDef11();
        case 12:
            return getDef12();
        case 13:
            return getDef13();
        case 14:
            return getDef14();
        case 15:
            return getDef15();
        case 16:
            return getDef16();
        case 17:
            return getDef17();
        case 18:
            return getDef18();
        case 19:
            return getDef19();
        case 20:
            return getDef20();
        }

        return null;
    }

    /**
     * 属性ecotype的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return Integer
     */
    public String getEcotype() {
        return ecotype;
    }

    /**
     * 属性email1的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getEmail1() {
        return email1;
    }

    /**
     * 属性email2的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getEmail2() {
        return email2;
    }

    /**
     * 属性email3的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getEmail3() {
        return email3;
    }

    /**
     * -------------------------------------------------- 功能：
     * 
     * 
     * 输入：
     * 
     * 输出：
     * 
     * 异常：
     * 
     * 补充：
     * 
     * 
     * 创建日期：(2003-10-31 8:57:13)
     * --------------------------------------------------
     * 
     * @return DZFDate
     */
    public DZFDate getEnddate() {
        return enddate;
    }

    /**
     * 返回数值对象的显示名称。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String 返回数值对象的显示名称。
     */
    public String getEntityName() {

        return "Corp";
    }

    /**
     * 属性fathercorp的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getFathercorp() {
        return fathercorp;
    }

    /**
     * 属性fax1的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getFax1() {
        return fax1;
    }

    /**
     * 属性fax2的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getFax2() {
        return fax2;
    }

    /**
     * 属性foreignname的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getForeignname() {
        return foreignname;
    }

    /**
     * 属性holdflag的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return DZFBoolean
     */
    public DZFBoolean getHoldflag() {
        return holdflag;
    }

    /**
     * -------------------------------------------------- 功能：
     * 
     * 
     * 输入：
     * 
     * 输出：
     * 
     * 异常：
     * 
     * 补充：
     * 
     * 
     * 创建日期：(2003-10-31 8:57:13)
     * --------------------------------------------------
     * 
     * @return String
     */
    public String getIdnumber() {
        return idnumber;
    }

    /**
     * 属性industry的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * 此处插入方法描述。 创建日期：(2004-12-12 15:24:21)
     * 
     * @return String
     */
    public String getInnercode() {
        return innercode;
    }

    /**
     * 属性ishasaccount 的Getter方法。
     * 
     * 创建日期：(2001-8-23)
     * 
     * @return DZFBoolean
     */
    public DZFBoolean getIshasaccount() {
        return ishasaccount;
    }

    /**
     * 属性isseal的Getter方法。
     * 
     * 创建日期：(2001-8-23)
     * 
     * @return DZFBoolean
     */
    public DZFBoolean getIsseal() {
        return isseal == null ? DZFBoolean.FALSE : isseal;
    }

    /**
     * 属性isworkingunit的Getter方法。
     * 
     * 创建日期：(2001-8-23)
     * 
     * @return DZFBoolean
     */
    public DZFBoolean getIsworkingunit() {
        return isworkingunit;
    }

    /**
     * 属性legalbodycode的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getLegalbodycode() {
        return legalbodycode;
    }

    /**
     * 属性linkman1的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getLinkman1() {
        return linkman1;
    }

    /**
     * 属性linkman2的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getLinkman2() {
        return linkman2;
    }

    /**
     * 属性linkman3的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getLinkman3() {
        return linkman3;
    }

    /**
     * 此处插入方法描述。 创建日期：(2004-12-12 15:24:42)
     * 
     * @return String
     */
    public String getMaxinnercode() {
        return maxinnercode;
    }

    /**
     * 属性memo的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 属性ownersharerate的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return DZFDouble
     */
    public DZFDouble getOwnersharerate() {
        if (ownersharerate == null)
            ownersharerate = new DZFDouble(0);
        return ownersharerate;
    }

    /**
     * 此处插入方法说明。 创建日期：(2004-4-26 13:52:51)
     * 
     * @return String
     */
    public String getParentPKFieldName() {
        return null;
    }

    /**
     * 属性phone1的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getPhone1() {
        return phone1;
    }

    /**
     * 属性phone2的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getPhone2() {
        return phone2;
    }

    /**
     * 属性phone3的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getPhone3() {
        return phone3;
    }

    /**
     * 属性pk_corp的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getPk_corp() {
        return pk_corp;
    }

    /**
     * 此处插入方法描述。 创建日期：(2004-12-13 10:50:11)
     * 
     * @return String
     */
    public String getPk_corpkind() {
        return pk_corpkind;
    }

    /**
     * -------------------------------------------------- 功能：
     * 
     * 
     * 输入：
     * 
     * 输出：
     * 
     * 异常：
     * 
     * 补充：
     * 
     * 
     * 创建日期：(2003-10-31 8:57:13)
     * --------------------------------------------------
     * 
     * @return String
     */
    public String getPk_currency() {
        return pk_currency;
    }

    /**
     * 此处插入方法说明。 创建日期：(2004-4-26 13:53:19)
     * 
     * @return String
     */
    public String getPKFieldName() {
        return "pk_corp";
    }

    /**
     * 属性postaddr的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getPostaddr() {
        return postaddr;
    }

    /**
     * 返回对象标识，用来唯一定位对象。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getPrimaryKey() {

        return pk_corp;
    }

    /**
     * 此处插入方法说明。 创建日期：(02-5-23 13:51:19)
     * 
     * @return String
     */
    public String getProvince() {
        return province;
    }

    /**
     * 此处插入方法说明。 创建日期：(02-5-23 13:51:19)
     * 
     * @return DZFDouble
     */
    public DZFDouble getRegcapital() {
        return regcapital;
    }

    /**
     * 此处插入方法描述。 创建日期：(2004-6-18 11:53:28)
     * 
     * @return String
     */
    public String getRegion() {
        return region;
    }

    /**
     * 属性saleaddr的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getSaleaddr() {
        return saleaddr;
    }

    /**
     * 此处插入方法说明。 创建日期：(2003-5-20 14:11:57)
     * 
     * @return DZFDate
     */
    public DZFDate getSealeddate() {
        return sealeddate;
    }

    /**
     * 此处插入方法说明。 创建日期：(2004-4-26 13:53:52)
     * 
     * @return String
     */
    public String getTableName() {
        return "bd_corp";
    }

    public Integer getTaxpayertype() {
        return taxpayertype;
    }

    public DZFDateTime getTs() {
        return ts;
    }

    /**
     * 属性unitcode的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getUnitcode() {
        return unitcode;
    }

    /**
     * 此处插入方法说明。 创建日期：(2004-4-26 11:36:28)
     * 
     * @return Integer
     */
    public String getUnitdistinction() {
        return unitdistinction;
    }

    /**
     * 属性unitname的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * 属性unitshortname的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getUnitshortname() {
        return unitshortname;
    }

    /**
     * 属性url的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * 属性zipcode的Getter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @return String
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * 属性showorder的Getter方法。
     * 
     * 创建日期：(2008-7-29)
     * 
     * @return int
     */
    public Integer getShoworder() {
        return showorder;
    }

    /**
     * accountproject的getter方法
     * 
     * @return
     */
    // public String getAccountproject() {
    // return accountproject;
    // }

    /**
     * 此处插入方法说明。 创建日期：(2003-5-20 16:39:25)
     * 
     * @return boolean
     */
    public boolean isBackup() {
        return m_isbackup;
    }

    /**
     * 判断是否是结算中心。 创建日期：(2004-3-12 12:49:52)
     * 
     * @return boolean
     */
    public boolean isSettleCenter() {
        // ISettleCenter iISettleCenter = (ISettleCenter)
        // NCLocator.getInstance().lookup(ISettleCenter.class.getName());
        // return iISettleCenter.isSettleCenter(getPk_corp());
        return false;
    }

    /**
     * 此处插入方法说明。 创建日期：(2003-5-20 16:39:25)
     * 
     * @param newM_isbackup
     *            boolean
     */
    public void setBackup(boolean newM_isbackup) {
        m_isbackup = newM_isbackup;
    }

    /**
     * -------------------------------------------------- 功能：
     * 
     * 
     * 输入：
     * 
     * 输出：
     * 
     * 异常：
     * 
     * 补充：
     * 
     * 
     * 创建日期：(2003-10-31 8:57:13)
     * --------------------------------------------------
     * 
     * @param newBegindate
     *            DZFDate
     */
    public void setBegindate(DZFDate newBegindate) {
        begindate = newBegindate;
    }

    /**
     * 此处插入方法说明。 创建日期：(02-5-23 13:51:19)
     * 
     * @param newBriefintro
     *            String
     */
    public void setBriefintro(String newBriefintro) {
        briefintro = newBriefintro;
    }

    /**
     * -------------------------------------------------- 功能：
     * 
     * 
     * 输入：
     * 
     * 输出：
     * 
     * 异常：
     * 
     * 补充：
     * 
     * 
     * 创建日期：(2003-10-31 8:57:13)
     * --------------------------------------------------
     * 
     * @param newChargeDeptCode
     *            String
     */
    public void setChargedeptcode(String newChargedeptcode) {
        chargedeptcode = newChargedeptcode;
    }

    /**
     * -------------------------------------------------- 功能：
     * 
     * 
     * 输入：
     * 
     * 输出：
     * 
     * 异常：
     * 
     * 补充：
     * 
     * 
     * 创建日期：(2003-10-31 8:57:13)
     * --------------------------------------------------
     * 
     * @param newChargedeptname
     *            String
     */
    public void setChargedeptname(String newChargedeptname) {
        chargedeptname = newChargedeptname;
    }

    /**
     * 此处插入方法说明。 创建日期：(02-5-23 13:51:19)
     * 
     * @param newCitycounty
     *            String
     */
    public void setCitycounty(String newCitycounty) {
        citycounty = newCitycounty;
    }

    /**
     * 此处插入方法说明。 创建日期：(2003-5-20 14:11:57)
     * 
     * @param newcorptype
     *            String
     */
    public void setCorptype(String newcorptype) {
        corptype = newcorptype;
    }

    /**
     * 此处插入方法说明。 创建日期：(02-5-23 13:51:19)
     * 
     * @param newCountryarea
     *            String
     */
    public void setCountryarea(String newCountryarea) {
        countryarea = newCountryarea;
    }

    /**
     * 此处插入方法说明。 创建日期：(02-4-18 16:05:35)
     * 
     * @param newCreatedate
     *            DZFDate
     */
    public void setCreatedate(DZFDate newCreatedate) {
        createdate = newCreatedate;
    }

    /**
     * 属性def1的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef1
     *            String
     */
    public void setDef1(String newDef1) {

        def1 = newDef1;
    }

    /**
     * 属性def10的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef10
     *            String
     */
    public void setDef10(String newDef10) {

        def10 = newDef10;
    }

    /**
     * 属性def11的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef11
     *            String
     */
    public void setDef11(String newDef11) {

        def11 = newDef11;
    }

    /**
     * 属性def12的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef12
     *            String
     */
    public void setDef12(String newDef12) {

        def12 = newDef12;
    }

    /**
     * 属性def13的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef13
     *            String
     */
    public void setDef13(String newDef13) {

        def13 = newDef13;
    }

    /**
     * 属性def14的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef14
     *            String
     */
    public void setDef14(String newDef14) {

        def14 = newDef14;
    }

    /**
     * 属性def15的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef15
     *            String
     */
    public void setDef15(String newDef15) {

        def15 = newDef15;
    }

    /**
     * 属性def16的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef16
     *            String
     */
    public void setDef16(String newDef16) {

        def16 = newDef16;
    }

    /**
     * 属性def17的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef17
     *            String
     */
    public void setDef17(String newDef17) {

        def17 = newDef17;
    }

    /**
     * 属性def18的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef18
     *            String
     */
    public void setDef18(String newDef18) {

        def18 = newDef18;
    }

    /**
     * 属性def19的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef19
     *            String
     */
    public void setDef19(String newDef19) {

        def19 = newDef19;
    }

    /**
     * 属性def2的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef2
     *            String
     */
    public void setDef2(String newDef2) {

        def2 = newDef2;
    }

    /**
     * 属性def20的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef20
     *            String
     */
    public void setDef20(String newDef20) {

        def20 = newDef20;
    }

    /**
     * 属性def3的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef3
     *            String
     */
    public void setDef3(String newDef3) {

        def3 = newDef3;
    }

    /**
     * 属性def4的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef4
     *            String
     */
    public void setDef4(String newDef4) {

        def4 = newDef4;
    }

    /**
     * 属性def5的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef5
     *            String
     */
    public void setDef5(String newDef5) {

        def5 = newDef5;
    }

    /**
     * 属性def6的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef6
     *            String
     */
    public void setDef6(String newDef6) {

        def6 = newDef6;
    }

    /**
     * 属性def7的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef7
     *            String
     */
    public void setDef7(String newDef7) {

        def7 = newDef7;
    }

    /**
     * 属性def8的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef8
     *            String
     */
    public void setDef8(String newDef8) {

        def8 = newDef8;
    }

    /**
     * 属性def9的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newDef9
     *            String
     */
    public void setDef9(String newDef9) {

        def9 = newDef9;
    }

    /**
     * 向vo里第index个自定义项设置值。 典型实现: switch(index){ case 1: setDef1(value);break;
     * case 2: setDef2(value);break; case 3: setDef3(value);break; ... }
     * 
     * 。 创建日期：(01-5-14 10:42:17)
     * 
     * @param value
     *            String
     * @param index
     *            int
     */
    public void setDefValue(String value, int index) {
        switch (index) {
        case 1:
            setDef1(value);
            break;
        case 2:
            setDef2(value);
            break;
        case 3:
            setDef3(value);
            break;
        case 4:
            setDef4(value);
            break;
        case 5:
            setDef5(value);
            break;
        case 6:
            setDef6(value);
            break;
        case 7:
            setDef7(value);
            break;
        case 8:
            setDef8(value);
            break;
        case 9:
            setDef9(value);
            break;
        case 10:
            setDef10(value);
            break;
        case 11:
            setDef11(value);
            break;
        case 12:
            setDef12(value);
            break;
        case 13:
            setDef13(value);
            break;
        case 14:
            setDef14(value);
            break;
        case 15:
            setDef15(value);
            break;
        case 16:
            setDef16(value);
            break;
        case 17:
            setDef17(value);
            break;
        case 18:
            setDef18(value);
            break;
        case 19:
            setDef19(value);
            break;
        case 20:
            setDef20(value);
            break;

        }
    }

    /**
     * 属性ecotype的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newEcotype
     *            Integer
     */
    public void setEcotype(String newEcotype) {

        ecotype = newEcotype;
    }

    /**
     * 属性email1的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newEmail1
     *            String
     */
    public void setEmail1(String newEmail1) {

        email1 = newEmail1;
    }

    /**
     * 属性email2的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newEmail2
     *            String
     */
    public void setEmail2(String newEmail2) {

        email2 = newEmail2;
    }

    /**
     * 属性email3的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newEmail3
     *            String
     */
    public void setEmail3(String newEmail3) {

        email3 = newEmail3;
    }

    /**
     * -------------------------------------------------- 功能：
     * 
     * 
     * 输入：
     * 
     * 输出：
     * 
     * 异常：
     * 
     * 补充：
     * 
     * 
     * 创建日期：(2003-10-31 8:57:13)
     * --------------------------------------------------
     * 
     * @param newEnddate
     *            DZFDate
     */
    public void setEnddate(DZFDate newEnddate) {
        enddate = newEnddate;
    }

    /**
     * 属性fathercorp的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newFathercorp
     *            String
     */
    public void setFathercorp(String newFathercorp) {

        fathercorp = newFathercorp;
    }

    /**
     * 属性fax1的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newFax1
     *            String
     */
    public void setFax1(String newFax1) {

        fax1 = newFax1;
    }

    /**
     * 属性fax2的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newFax2
     *            String
     */
    public void setFax2(String newFax2) {

        fax2 = newFax2;
    }

    /**
     * 属性foreignname的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newForeignname
     *            String
     */
    public void setForeignname(String newForeignname) {

        foreignname = newForeignname;
    }

    /**
     * 属性holdflag的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newHoldflag
     *            DZFBoolean
     */
    public void setHoldflag(DZFBoolean newHoldflag) {

        holdflag = newHoldflag;
    }

    /**
     * -------------------------------------------------- 功能：
     * 
     * 
     * 输入：
     * 
     * 输出：
     * 
     * 异常：
     * 
     * 补充：
     * 
     * 
     * 创建日期：(2003-10-31 8:57:13)
     * --------------------------------------------------
     * 
     * @param newIdnumber
     *            String
     */
    public void setIdnumber(String newIdnumber) {
        idnumber = newIdnumber;
    }

    /**
     * 属性industry的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newIndustry
     *            String
     */
    public void setIndustry(String newIndustry) {

        industry = newIndustry;
    }

    /**
     * 此处插入方法描述。 创建日期：(2004-12-12 15:24:21)
     * 
     * @param newInnercode
     *            String
     */
    public void setInnercode(String newInnercode) {
        innercode = newInnercode;
    }

    /**
     * 属性ishasaccount 的setter方法。
     * 
     * 创建日期：(2001-8-23)
     * 
     * @param newishasaccount
     *            DZFBoolean
     */
    public void setIshasaccount(DZFBoolean newIshasaccount) {

        ishasaccount = newIshasaccount;
    }

    /**
     * 属性isseal的setter方法。
     * 
     * 创建日期：(2001-8-23)
     * 
     * @param newisseal
     *            DZFBoolean
     */
    public void setIsseal(DZFBoolean newIsseal) {

        isseal = newIsseal;
    }

    /**
     * 属性isworkingunit的setter方法。
     * 
     * 创建日期：(2001-8-23)
     * 
     * @param newisworkingunit
     *            DZFBoolean
     */
    public void setIsworkingunit(DZFBoolean newIsworkingunit) {

        isworkingunit = newIsworkingunit;
    }

    /**
     * issettlecenter的setter
     * 
     * @param DZFBoolean
     */
    public void setIssettlecenter(DZFBoolean issettlecenter) {
        this.issettlecenter = issettlecenter;
    }

    /**
     * 属性legalbodycode的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newLegalbodycode
     *            String
     */
    public void setLegalbodycode(String newLegalbodycode) {

        legalbodycode = newLegalbodycode;
    }

    /**
     * 属性linkman1的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newLinkman1
     *            String
     */
    public void setLinkman1(String newLinkman1) {

        linkman1 = newLinkman1;
    }

    /**
     * 属性linkman2的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newLinkman2
     *            String
     */
    public void setLinkman2(String newLinkman2) {

        linkman2 = newLinkman2;
    }

    /**
     * 属性linkman3的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newLinkman3
     *            String
     */
    public void setLinkman3(String newLinkman3) {

        linkman3 = newLinkman3;
    }

    /**
     * 此处插入方法描述。 创建日期：(2004-12-12 15:24:42)
     * 
     * @param newMaxinnercode
     *            String
     */
    public void setMaxinnercode(String newMaxinnercode) {
        maxinnercode = newMaxinnercode;
    }

    /**
     * 属性memo的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newMemo
     *            String
     */
    public void setMemo(String newMemo) {

        memo = newMemo;
    }

    /**
     * 属性ownersharerate的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newOwnersharerate
     *            DZFDouble
     */
    public void setOwnersharerate(DZFDouble newOwnersharerate) {

        ownersharerate = newOwnersharerate;
    }

    /**
     * 属性phone1的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newPhone1
     *            String
     */
    public void setPhone1(String newPhone1) {

        phone1 = newPhone1;
    }

    /**
     * 属性phone2的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newPhone2
     *            String
     */
    public void setPhone2(String newPhone2) {

        phone2 = newPhone2;
    }

    /**
     * 属性phone3的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newPhone3
     *            String
     */
    public void setPhone3(String newPhone3) {

        phone3 = newPhone3;
    }

    /**
     * 属性pk_corp的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newPk_corp
     *            String
     */
    public void setPk_corp(String newPk_corp) {

        pk_corp = newPk_corp;
    }

    /**
     * 此处插入方法描述。 创建日期：(2004-12-13 10:50:11)
     * 
     * @param newPk_corpkind
     *            String
     */
    public void setPk_corpkind(String newPk_corpkind) {
        pk_corpkind = newPk_corpkind;
    }

    /**
     * -------------------------------------------------- 功能：
     * 
     * 
     * 输入：
     * 
     * 输出：
     * 
     * 异常：
     * 
     * 补充：
     * 
     * 
     * 创建日期：(2003-10-31 8:57:13)
     * --------------------------------------------------
     * 
     * @param newPk_currency
     *            String
     */
    public void setPk_currency(String newPk_currency) {
        pk_currency = newPk_currency;
    }

    /**
     * 属性postaddr的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newPostaddr
     *            String
     */
    public void setPostaddr(String newPostaddr) {

        postaddr = newPostaddr;
    }

    /**
     * 设置对象标识，用来唯一定位对象。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param pk_corp
     *            String
     */
    public void setPrimaryKey(String newPk_corp) {

        pk_corp = newPk_corp;
    }

    /**
     * 此处插入方法说明。 创建日期：(02-5-23 13:51:19)
     * 
     * @param newProvince
     *            String
     */
    public void setProvince(String newProvince) {
        province = newProvince;
    }

    /**
     * 此处插入方法说明。 创建日期：(02-5-23 13:51:19)
     * 
     * @param newRegcapital
     *            DZFDouble
     */
    public void setRegcapital(DZFDouble newRegcapital) {
        regcapital = newRegcapital;
    }

    /**
     * 此处插入方法描述。 创建日期：(2004-6-18 11:53:54)
     * 
     * @param newRegion
     *            String
     */
    public void setRegion(String newRegion) {

        region = newRegion;
    }

    /**
     * 属性saleaddr的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newSaleaddr
     *            String
     */
    public void setSaleaddr(String newSaleaddr) {

        saleaddr = newSaleaddr;
    }

    /**
     * 此处插入方法说明。 创建日期：(2003-5-20 14:11:57)
     * 
     * @param newsealeddate
     *            DZFDate
     */
    public void setSealeddate(DZFDate newsealeddate) {
        sealeddate = newsealeddate;
    }

    public void setTaxpayertype(Integer taxpayertype) {
        this.taxpayertype = taxpayertype;
    }

    public void setTs(DZFDateTime ts) {
        this.ts = ts;
    }

    /**
     * 属性unitcode的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newUnitcode
     *            String
     */
    public void setUnitcode(String newUnitcode) {

        unitcode = newUnitcode;
    }

    /**
     * 此处插入方法说明。 创建日期：(2004-4-26 11:36:59)
     * 
     * @param newUnitdistinction
     *            Integer
     */
    public void setUnitdistinction(String newUnitdistinction) {
        unitdistinction = newUnitdistinction;
    }

    /**
     * 属性unitname的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newUnitname
     *            String
     */
    public void setUnitname(String newUnitname) {

        unitname = newUnitname;
    }

    /**
     * 属性unitshortname的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newUnitshortname
     *            String
     */
    public void setUnitshortname(String newUnitshortname) {

        unitshortname = newUnitshortname;
    }

    /**
     * 属性url的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newUrl
     *            String
     */
    public void setUrl(String newUrl) {

        url = newUrl;
    }

    /**
     * 属性zipcode的setter方法。
     * 
     * 创建日期：(2001-5-16)
     * 
     * @param newZipcode
     *            String
     */
    public void setZipcode(String newZipcode) {

        zipcode = newZipcode;
    }

    /**
     * 属性showorder_id的setter方法。
     * 
     * 创建日期：(2008-7-29)
     * 
     * @param showorder
     *            int
     */
    public void setShoworder(Integer showorder) {

        this.showorder = showorder;
    }

    /**
     * accountproject的setter方法
     * 
     * @return
     */
    // public void setAccountproject(String accountproject) {
    // this.accountproject = accountproject;
    // }

    /**
     * 返回公司名称。 创建日期：(2001-8-28 16:20:40)
     * 
     * @return String
     */
    public String toString() {
        return unitname;
    }

    public DZFBoolean getIsaccountcorp() {
        return isaccountcorp;
    }

    public void setIsaccountcorp(DZFBoolean isaccountcorp) {
        this.isaccountcorp = isaccountcorp;
    }

    public DZFBoolean getIsdatacorp() {
        return isdatacorp;
    }

    public void setIsdatacorp(DZFBoolean isdatacorp) {
        this.isdatacorp = isdatacorp;
    }

    public DZFBoolean getIscurr() {
        return iscurr;
    }

    public void setIscurr(DZFBoolean iscurr) {
        this.iscurr = iscurr;
    }

    public Integer getIcostforwardstyle() {
        return icostforwardstyle;
    }

    public void setIcostforwardstyle(Integer icostforwardstyle) {
        this.icostforwardstyle = icostforwardstyle;
    }

    public DZFBoolean getBbuildic() {
        return bbuildic;
    }

    public void setBbuildic(DZFBoolean bbuildic) {
        this.bbuildic = bbuildic;
    }

    public String getTaxcode() {
        return taxcode;
    }

    public void setTaxcode(String taxcode) {
        this.taxcode = taxcode;
    }

    public DZFDate getBusibegindate() {
        return busibegindate;
    }

    public void setBusibegindate(DZFDate busibegindate) {
        this.busibegindate = busibegindate;
    }

    public DZFDate getBusienddate() {
        return busienddate;
    }

    public void setBusienddate(DZFDate busienddate) {
        this.busienddate = busienddate;
    }

    public DZFBoolean getIsuseretail() {
        return isuseretail == null ? DZFBoolean.FALSE : isuseretail;
    }

    public void setIsuseretail(DZFBoolean isuseretail) {
        this.isuseretail = isuseretail;
    }

    public DZFDate getIcbegindate() {
        return icbegindate;
    }

    public void setIcbegindate(DZFDate icbegindate) {
        this.icbegindate = icbegindate;
    }

    public String getIndusname() {
        return indusname;
    }

    public void setIndusname(String indusname) {
        this.indusname = indusname;
    }

    public String getCtypename() {
        return ctypename;
    }

    public void setCtypename(String ctypename) {
        this.ctypename = ctypename;
    }

    public boolean isM_isbackup() {
        return m_isbackup;
    }

    public void setM_isbackup(boolean m_isbackup) {
        this.m_isbackup = m_isbackup;
    }

    public String getCropArea() {
        return cropArea;
    }

    public void setCropArea(String cropArea) {
        this.cropArea = cropArea;
    }

    public String getVcorporationid() {
        return vcorporationid;
    }

    public void setVcorporationid(String vcorporationid) {
        this.vcorporationid = vcorporationid;
    }

    public DZFBoolean getIspersonal() {
        return ispersonal;
    }

    public void setIspersonal(DZFBoolean ispersonal) {
        this.ispersonal = ispersonal;
    }

    public String getVorgcode() {
        return vorgcode;
    }

    public void setVorgcode(String vorgcode) {
        this.vorgcode = vorgcode;
    }

    public String getVbusilicode() {
        return vbusilicode;
    }

    public void setVbusilicode(String vbusilicode) {
        this.vbusilicode = vbusilicode;
    }

    public DZFDate getDlicexpdate() {
        return dlicexpdate;
    }

    public void setDlicexpdate(DZFDate dlicexpdate) {
        this.dlicexpdate = dlicexpdate;
    }

    public String getVaccountopen() {
        return vaccountopen;
    }

    public void setVaccountopen(String vaccountopen) {
        this.vaccountopen = vaccountopen;
    }

    public String getVcompcode() {
        return vcompcode;
    }

    public void setVcompcode(String vcompcode) {
        this.vcompcode = vcompcode;
    }

    public DZFDate getDcompdate() {
        return dcompdate;
    }

    public void setDcompdate(DZFDate dcompdate) {
        this.dcompdate = dcompdate;
    }

    public String getVsoccrecode() {
        return vsoccrecode;
    }

    public void setVsoccrecode(String vsoccrecode) {
        this.vsoccrecode = vsoccrecode;
    }

    public String getVbankname() {
        return vbankname;
    }

    public void setVbankname(String vbankname) {
        this.vbankname = vbankname;
    }

    public String getVbankcode() {
        return vbankcode;
    }

    public void setVbankcode(String vbankcode) {
        this.vbankcode = vbankcode;
    }

    public String getVbankaddr() {
        return vbankaddr;
    }

    public void setVbankaddr(String vbankaddr) {
        this.vbankaddr = vbankaddr;
    }

    public String getVbankpos() {
        return vbankpos;
    }

    public void setVbankpos(String vbankpos) {
        this.vbankpos = vbankpos;
    }

    public String getVbusitype() {
        return vbusitype;
    }

    public void setVbusitype(String vbusitype) {
        this.vbusitype = vbusitype;
    }

    public String getVfilecode() {
        return vfilecode;
    }

    public void setVfilecode(String vfilecode) {
        this.vfilecode = vfilecode;
    }

    public Integer getVprovince() {
        return vprovince;
    }

    public void setVprovince(Integer vprovince) {
        this.vprovince = vprovince;
    }

    public Integer getVcity() {
        return vcity;
    }

    public void setVcity(Integer vcity) {
        this.vcity = vcity;
    }

    public Integer getVarea() {
        return varea;
    }

    public void setVarea(Integer varea) {
        this.varea = varea;
    }

    public String getVcustsource() {
        return vcustsource;
    }

    public void setVcustsource(String vcustsource) {
        this.vcustsource = vcustsource;
    }

    public String getVsourcenote() {
        return vsourcenote;
    }

    public void setVsourcenote(String vsourcenote) {
        this.vsourcenote = vsourcenote;
    }

    public String getVcustmainbusi() {
        return vcustmainbusi;
    }

    public void setVcustmainbusi(String vcustmainbusi) {
        this.vcustmainbusi = vcustmainbusi;
    }

    public String getVrespaccount() {
        return vrespaccount;
    }

    public void setVrespaccount(String vrespaccount) {
        this.vrespaccount = vrespaccount;
    }

    public String getVresptel() {
        return vresptel;
    }

    public void setVresptel(String vresptel) {
        this.vresptel = vresptel;
    }

    public String getVcustothertel() {
        return vcustothertel;
    }

    public void setVcustothertel(String vcustothertel) {
        this.vcustothertel = vcustothertel;
    }

    public String getVstatetaxplace() {
        return vstatetaxplace;
    }

    public void setVstatetaxplace(String vstatetaxplace) {
        this.vstatetaxplace = vstatetaxplace;
    }

    public String getVstatetaxaddr() {
        return vstatetaxaddr;
    }

    public void setVstatetaxaddr(String vstatetaxaddr) {
        this.vstatetaxaddr = vstatetaxaddr;
    }

    public String getVstatetaxper() {
        return vstatetaxper;
    }

    public void setVstatetaxper(String vstatetaxper) {
        this.vstatetaxper = vstatetaxper;
    }

    public String getVstatetaxpertel() {
        return vstatetaxpertel;
    }

    public void setVstatetaxpertel(String vstatetaxpertel) {
        this.vstatetaxpertel = vstatetaxpertel;
    }

    public String getVstatetaxpwd() {
        return vstatetaxpwd;
    }

    public void setVstatetaxpwd(String vstatetaxpwd) {
        this.vstatetaxpwd = vstatetaxpwd;
    }

    public DZFDate getDstatetaxdate() {
        return dstatetaxdate;
    }

    public void setDstatetaxdate(DZFDate dstatetaxdate) {
        this.dstatetaxdate = dstatetaxdate;
    }

    public String getVlocaltaxcode() {
        return vlocaltaxcode;
    }

    public void setVlocaltaxcode(String vlocaltaxcode) {
        this.vlocaltaxcode = vlocaltaxcode;
    }

    public String getVlocaltaxplace() {
        return vlocaltaxplace;
    }

    public void setVlocaltaxplace(String vlocaltaxplace) {
        this.vlocaltaxplace = vlocaltaxplace;
    }

    public String getVlocaltaxper() {
        return vlocaltaxper;
    }

    public void setVlocaltaxper(String vlocaltaxper) {
        this.vlocaltaxper = vlocaltaxper;
    }

    public String getVlocaltaxpertel() {
        return vlocaltaxpertel;
    }

    public void setVlocaltaxpertel(String vlocaltaxpertel) {
        this.vlocaltaxpertel = vlocaltaxpertel;
    }

    public String getVlocaltaxaddr() {
        return vlocaltaxaddr;
    }

    public void setVlocaltaxaddr(String vlocaltaxaddr) {
        this.vlocaltaxaddr = vlocaltaxaddr;
    }

    public String getVlocaltaxpwd() {
        return vlocaltaxpwd;
    }

    public void setVlocaltaxpwd(String vlocaltaxpwd) {
        this.vlocaltaxpwd = vlocaltaxpwd;
    }

    public Integer getVcorporatetax() {
        return vcorporatetax;
    }

    public void setVcorporatetax(Integer vcorporatetax) {
        this.vcorporatetax = vcorporatetax;
    }

    public DZFBoolean getVtaxtype() {
        return vtaxtype;
    }

    public void setVtaxtype(DZFBoolean vtaxtype) {
        this.vtaxtype = vtaxtype;
    }

    public String getVtaxcode() {
        return vtaxcode;
    }

    public void setVtaxcode(String vtaxcode) {
        this.vtaxcode = vtaxcode;
    }

    public String getVgenvohtype() {
        return vgenvohtype;
    }

    public void setVgenvohtype(String vgenvohtype) {
        this.vgenvohtype = vgenvohtype;
    }

    public String getVsuperaccount() {
        return vsuperaccount;
    }

    public void setVsuperaccount(String vsuperaccount) {
        this.vsuperaccount = vsuperaccount;
    }

    public String getVwqaccount() {
        return vwqaccount;
    }

    public void setVwqaccount(String vwqaccount) {
        this.vwqaccount = vwqaccount;
    }

    public DZFDate getDestablishdate() {
        return destablishdate;
    }

    public void setDestablishdate(DZFDate destablishdate) {
        this.destablishdate = destablishdate;
    }

    public DZFBoolean getIsukey() {
        return isukey;
    }

    public void setIsukey(DZFBoolean isukey) {
        this.isukey = isukey;
    }

    public String getPk_source() {
        return pk_source;
    }

    public void setPk_source(String pk_source) {
        this.pk_source = pk_source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPyfirstcomb() {
        return pyfirstcomb;
    }

    public void setPyfirstcomb(String pyfirstcomb) {
        this.pyfirstcomb = pyfirstcomb;
    }

    public DZFDate getDscodedate() {
        return dscodedate;
    }

    public void setDscodedate(DZFDate dscodedate) {
        this.dscodedate = dscodedate;
    }

    public DZFDate getDukeydate() {
        return dukeydate;
    }

    public void setDukeydate(DZFDate dukeydate) {
        this.dukeydate = dukeydate;
    }

    public String getVpersonalpwd() {
        return vpersonalpwd;
    }

    public void setVpersonalpwd(String vpersonalpwd) {
        this.vpersonalpwd = vpersonalpwd;
    }

    public String getPcountname() {
        return pcountname;
    }

    public void setPcountname(String pcountname) {
        this.pcountname = pcountname;
    }

    public String getWqcountname() {
        return wqcountname;
    }

    public void setWqcountname(String wqcountname) {
        this.wqcountname = wqcountname;
    }

    public DZFBoolean isIssmall() {
        return issmall;
    }

    public void setIssmall(DZFBoolean issmall) {
        this.issmall = issmall;
    }

    public String getEstablishtime() {
        return establishtime;
    }

    public void setEstablishtime(String establishtime) {
        this.establishtime = establishtime;
    }

    public String getAccountcoderule() {
        return accountcoderule;
    }

    public void setAccountcoderule(String accountcoderule) {
        this.accountcoderule = accountcoderule;
    }

    public DZFDouble getRegistcapital() {
        return registcapital;
    }

    public void setRegistcapital(DZFDouble registcapital) {
        this.registcapital = registcapital;
    }

    public DZFBoolean getIskwxjr() {
        return iskwxjr;
    }

    public void setIskwxjr(DZFBoolean iskwxjr) {
        this.iskwxjr = iskwxjr;
    }

    public DZFBoolean getIssmall() {
        return issmall;
    }

    public Integer getIbuildicstyle() {
        return ibuildicstyle;
    }

    public void setIbuildicstyle(Integer ibuildicstyle) {
        this.ibuildicstyle = ibuildicstyle;
    }

    public String getAccbooks() {
        return accbooks;
    }

    public void setAccbooks(String accbooks) {
        this.accbooks = accbooks;
    }

    public String getBooksname() {
        return booksname;
    }

    public void setBooksname(String booksname) {
        this.booksname = booksname;
    }

    public String getForeignid() {
        return foreignid;
    }

    public void setForeignid(String foreignid) {
        this.foreignid = foreignid;
    }

    public DZFDouble getCitybuildtax() {
        return citybuildtax;
    }

    public void setCitybuildtax(DZFDouble citybuildtax) {
        this.citybuildtax = citybuildtax;
    }

    public DZFBoolean getIsmaintainedtax() {
        return ismaintainedtax;
    }

    public void setIsmaintainedtax(DZFBoolean ismaintainedtax) {
        this.ismaintainedtax = ismaintainedtax;
    }

    public DZFDateTime getLastmodifytime() {
        return lastmodifytime;
    }

    public void setLastmodifytime(DZFDateTime lastmodifytime) {
        this.lastmodifytime = lastmodifytime;
    }

    public String getIndustrycode() {
        return industrycode;
    }

    public void setIndustrycode(String industrycode) {
        this.industrycode = industrycode;
    }

    public String getTaxcontrmachtype() {
        return taxcontrmachtype;
    }

    public void setTaxcontrmachtype(String taxcontrmachtype) {
        this.taxcontrmachtype = taxcontrmachtype;
    }

    public DZFBoolean getIsdkfp() {
        return isdkfp;
    }

    public void setIsdkfp(DZFBoolean isdkfp) {
        this.isdkfp = isdkfp;
    }

    public DZFBoolean getIsdbbx() {
        return isdbbx;
    }

    public void setIsdbbx(DZFBoolean isdbbx) {
        this.isdbbx = isdbbx;
    }

    public Integer getIsxrq() {
        return isxrq;
    }

    public void setIsxrq(Integer isxrq) {
        this.isxrq = isxrq;
    }

    public DZFDate getDrdsj() {
        return drdsj;
    }

    public void setDrdsj(DZFDate drdsj) {
        this.drdsj = drdsj;
    }

    public DZFBoolean getIsywskp() {
        return isywskp;
    }

    public void setIsywskp(DZFBoolean isywskp) {
        this.isywskp = isywskp;
    }

    public Integer getIfwgs() {
        return ifwgs;
    }

    public void setIfwgs(Integer ifwgs) {
        this.ifwgs = ifwgs;
    }

    public Integer getIsdsbsjg() {
        return isdsbsjg;
    }

    public void setIsdsbsjg(Integer isdsbsjg) {
        this.isdsbsjg = isdsbsjg;
    }

    public Integer getIkjzc() {
        return ikjzc;
    }

    public void setIkjzc(Integer ikjzc) {
        this.ikjzc = ikjzc;
    }

    public DZFDate getDrzsj() {
        return drzsj;
    }

    public void setDrzsj(DZFDate drzsj) {
        this.drzsj = drzsj;
    }

    public String getVschlnd() {
        return vschlnd;
    }

    public void setVschlnd(String vschlnd) {
        this.vschlnd = vschlnd;
    }

    public String getVyhzc() {
        return vyhzc;
    }

    public void setVyhzc(String vyhzc) {
        this.vyhzc = vyhzc;
    }

    public String getDlsname() {
        return dlsname;
    }

    public void setDlsname(String dlsname) {
        this.dlsname = dlsname;
    }

    public String getAccountProgress() {
        return accountProgress;
    }

    public void setAccountProgress(String accountProgress) {
        this.accountProgress = accountProgress;
    }

    public DZFBoolean getIsneedocr() {
        return isneedocr;
    }

    public void setIsneedocr(DZFBoolean isneedocr) {
        this.isneedocr = isneedocr;
    }

    public DZFBoolean getIsneedappro() {
        return isneedappro;
    }

    public void setIsneedappro(DZFBoolean isneedappro) {
        this.isneedappro = isneedappro;
    }

    public String getDjshfs() {
        return djshfs;
    }

    public void setDjshfs(String djshfs) {
        this.djshfs = djshfs;
    }

    public T[] getCorpRoleVos() {
        return corpRoleVos;
    }

    public void setCorpRoleVos(T[] corpRoleVos) {
        this.corpRoleVos = corpRoleVos;
    }

    public T[] getCorpCredenVos() {
        return corpCredenVos;
    }

    public void setCorpCredenVos(T[] corpCredenVos) {
        this.corpCredenVos = corpCredenVos;
    }

    public T[] getCorpDocVos() {
        return corpDocVos;
    }

    public void setCorpDocVos(T[] corpDocVos) {
        this.corpDocVos = corpDocVos;
    }

    public T[] getCorpTaxInfoVos() {
        return corpTaxInfoVos;
    }

    public void setCorpTaxInfoVos(T[] corpTaxInfoVos) {
        this.corpTaxInfoVos = corpTaxInfoVos;
    }

    public T[] getCorpSholderVos() {
        return corpSholderVos;
    }

    public void setCorpSholderVos(T[] corpSholderVos) {
        this.corpSholderVos = corpSholderVos;
    }

	public String getAccountProgressDate() {
		return accountProgressDate;
	}

	public void setAccountProgressDate(String accountProgressDate) {
		this.accountProgressDate = accountProgressDate;
	}
}
