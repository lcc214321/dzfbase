package com.dzf.pub;

import java.io.File;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.dzf.pub.lang.DZFDouble;

/**
 * 常用公共类
 * @author liangy
 *
 */
public class Common {
	public static String virtualcorp = "10n3";
	public static String fathercorp = "10k:";
	public static String tempidcreate="appuse";
	public static String appzipfile = "uploadfile";
	public static String format(Object val) {
		String value = "0.00";
		if(val != null){

			NumberFormat fm = NumberFormat.getNumberInstance();
			
			fm.setRoundingMode(RoundingMode.HALF_UP);
			fm.setMinimumFractionDigits(2);
			fm.setMaximumFractionDigits(2);
			
			value = fm.format(new DZFDouble(val.toString()));
		}
		return value; 
	}
	public static final String subBetSubjectcode = "1200";
	public static final String[] betSubjectcodes = new String[]{"1122", "1123", "1221", "2202", "2203", "2241"};  
	public static void print(HttpServletRequest r){
	Enumeration<String> en=	r.getAttributeNames();
	while(en.hasMoreElements()){
		System.out.println(en.nextElement());
	}
	
	}
	public static String imageBasePath = "";
	  static
	  {
	    imageBasePath ="";
	    if (!imageBasePath.endsWith("/"))
	      imageBasePath += "/";
	    imageBasePath += "ImageUpload/";
	    new File(imageBasePath).mkdir();
	  }
	public static final double EARTH_RADIUS = 6378137;
//	public static String getToken(String usercode)
//			throws Exception {
////		InvocationInfoProxy.getInstance().setUserCode(usercode);
////		ISecurityTokenCallback sc = (ISecurityTokenCallback) NCLocator.getInstance().lookup(ISecurityTokenCallback.class);
////		sc.token(usercode.getBytes("UTF-8"),genSessionID(new Random(2015)).getBytes("UTF-8"));
//		String token = Base64.encode(NetStreamContext.getToken());
//		return token;
//	}
	public static String genSessionID(Random m_rand) {
		long rand = m_rand.nextLong();
		long currentTimeMillis = System.currentTimeMillis();
		return "" + currentTimeMillis + rand;
	}
	 private static double rad(double d)
	    {
	       return d * Math.PI / 180.0;
	    }
	    
	    /**
	     * �������侭γ����꣨doubleֵ���������������룬��λΪ��
	     * @param lng1
	     * @param lat1
	     * @param lng2
	     * @param lat2
	     * @return
	     */
	    public static double getDistance(double lng1, double lat1, double lng2, double lat2)
	    {
	       double radLat1 = rad(lat1);
	       double radLat2 = rad(lat2);
	       double a = radLat1 - radLat2;
	       double b = rad(lng1) - rad(lng2);
	       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	        Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	       s = s * EARTH_RADIUS;
	       s = Math.round(s * 10000) / 10000;
	       return s;
	    }
}
