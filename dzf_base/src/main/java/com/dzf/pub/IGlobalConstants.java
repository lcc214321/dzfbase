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
	
//	用户登陆失败次数，锁定
	public final static int lock_fail_login	= 6;
	
//	用户登陆失败次数，验证码
	public final static int verify_fail_login = 3;
	
//	用户登陆失败锁定时间，分钟
	public final static int lock_login_min = 15;
	
	//强制退出信息
	public final static String logout_msg = "logout_msg";
	
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
//		用户退出原因
		public final static Map<Integer,String> logoutType = new TreeMap<Integer,String>(){{
		    put(0, "未退出");
		    put(1, "用户正常退出");
		    put(2, "强制退出");
		    put(3, "session超出");
		    }};
		//			用户退出原因
		public final static Map<Integer,String> loginStatus = new TreeMap<Integer,String>(){{
		    put(0, "登陆失败");
			put(1, "登陆成功");
		}};
	    
//	    合同付款方式
	 public final static Map<Integer,String> contractPayMode = new TreeMap<Integer, String>(){{
		 put(1, "月付");
		 put(2, "季付");
		 put(3, "年付");
		 put(4, "一次付");
		 put(5, "半年付");
	 }};
	    
    public static String DZF_KJ="dzf_kj";
    public static String SYS_DZF="sys_dzf";
    public static String SYS_DATA="sys_data";
    public static String ADMIN_KJ="admin_kj";
    
    public static String POWER_MAP="powerMap";
    
    public static String FONTPATH="/data1/webApp/font/SIMKAI.TTF";//打印字体  本地C:/windows/font/simkai.ttf
}
