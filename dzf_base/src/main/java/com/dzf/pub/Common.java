package com.dzf.pub;

import java.math.RoundingMode;
import java.text.NumberFormat;

import com.dzf.pub.lang.DZFDouble;

/**
 * 常用公共类
 * @author liangy
 *
 */
public class Common {
//	public static final double EARTH_RADIUS = 6378.137;
//	public static String getToken(String usercode)
//			throws Exception {
////		InvocationInfoProxy.getInstance().setUserCode(usercode);
////		ISecurityTokenCallback sc = (ISecurityTokenCallback) NCLocator.getInstance().lookup(ISecurityTokenCallback.class);
////		sc.token(usercode.getBytes("UTF-8"),genSessionID(new Random(2015)).getBytes("UTF-8"));
//		String token = Base64.encode(NetStreamContext.getToken());
//		return token;
//	}
//	public static String genSessionID(Random m_rand) {
//		long rand = m_rand.nextLong();
//		long currentTimeMillis = System.currentTimeMillis();
//		return "" + currentTimeMillis + rand;
//	}
//    public static double getDistance(UFDouble  ufd)
//    {
//      ufd=ufd.multiply(new UFDouble(2.0*EARTH_RADIUS), -4);
//       return ufd.doubleValue();
//    }
//	 private static double rad(double d)
//	    {
//	       return d * Math.PI / 180.0;
//	    }
	    
	    /**
	     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	     * @param lng1
	     * @param lat1
	     * @param lng2
	     * @param lat2
	     * @return
	     */
	 
//	    public static double getDistance(double lng1, double lat1, double lng2, double lat2)
//	    {
//	       double radLat1 = rad(lat1);
//	       double radLat2 = rad(lat2);
//	       double radLatDif = radLat1 - radLat2;
//	       double redLogDif = rad(lng1) - rad(lng2);
//	       double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(radLatDif/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(redLogDif/2),2)));
//	       distance = distance * 6378137;
//	       distance = Math.round(distance * 10000) / 10000;
//	       return distance;
//	    }
		/**
		 * 千分位格式化
		 * @param val
		 * @return
		 */
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
}
