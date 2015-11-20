package com.dzf.pub;

import java.util.Map;
import java.util.TreeMap;


public interface IGlobalConstants extends IDefaultValue{

    //用户登录session
	public final static String login_user = "login_user";
	
	//用户登录日期session
	public final static String login_date = "login_date";
	
	//登录公司session
	public final static String login_corp = "login_corp";
	
//	获取币种cache的公司id
	public final static String currency_corp = DefaultGroup;
	
	public final static String RMB_currency_id = "00000100AA10000000000BKT";
	public final static String login_token = "login_token";
//	现金类科目
	public final static Map<String,Boolean> xjll_km = new TreeMap<String,Boolean>(){{
	    put("1001", true);
	    put("1002", true);
	    put("1009", true);
	    put("1012", true);
	    }};
	    
    public static String DZF_KJ="dzf_kj";
    public static String SYS_DZF="sys_dzf";
    public static String SYS_DATA="sys_data";
    public static String ADMIN_KJ="admin_kj";
    
    public static String POWER_MAP="powerMap";
}
