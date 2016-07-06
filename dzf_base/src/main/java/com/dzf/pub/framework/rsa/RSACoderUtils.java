package com.dzf.pub.framework.rsa;

import java.util.Arrays;
import java.util.HashSet;
import javax.servlet.http.HttpSession;

import com.dzf.framework.comn.IOUtils;
import com.dzf.pub.WiseRunException;
import com.dzf.pub.cache.RsaKeyCache;
import com.dzf.pub.DZFWarpException;
import com.dzf.pub.IGlobalConstants;
import com.dzf.pub.MD516;
import com.dzf.pub.StringUtil;


public class RSACoderUtils {

	public RSACoderUtils() {
	}
//    private static String publicKey;  
//    private static String privateKey;  

//    static {  
//       
//
//        try {
//        	 Map<String, Object> keyMap = RSACoder.initKey();  
//			publicKey = RSACoder.getPublicKey(keyMap);
//			privateKey = RSACoder.getPrivateKey(keyMap); 
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//        
//}

  //加密
  	public static void createToken(HttpSession hs){
  		try{
  			String userid=(String) hs.getAttribute(IGlobalConstants.login_user);
  	  		String corp=(String) hs.getAttribute(IGlobalConstants.login_corp);
  	  		corp = getToken(hs, userid, corp,(HashSet<Integer>) hs.getAttribute(IGlobalConstants.POWER_MAP));  
  	  		hs.setAttribute(IGlobalConstants.login_token,corp);
  		}catch(Exception e){
  			throw new WiseRunException(e);
  		}
  		
  	}
  	public static boolean validateToken(HttpSession hs){
  		try{
  			String userid=(String) hs.getAttribute(IGlobalConstants.login_user);
  	  		String corp=(String) hs.getAttribute(IGlobalConstants.login_corp);
  	  	HashSet<Integer> set=(HashSet<Integer>) hs.getAttribute(IGlobalConstants.POWER_MAP);
  	  	return validateToken(hs,userid,corp,set);
  		}catch(Exception e){
  			throw new WiseRunException(e);
  		}
  	}
  	//加密
  	public static boolean validateToken(HttpSession hs,String uid,String cp,HashSet<Integer> set){
  		try{
  			String userid=(String) hs.getAttribute(IGlobalConstants.login_user);
  	  		String corp=(String) hs.getAttribute(IGlobalConstants.login_corp);
  	  		byte[] s1 = getTokenString(hs, userid, corp,(HashSet<Integer>) hs.getAttribute(IGlobalConstants.POWER_MAP));
  	  		String s2=(String) hs.getAttribute(IGlobalConstants.login_token);
  	  		byte[] s3=getValue(s2);
  	  		s2=new String(s3);
  	  		s3=null;
  	  		boolean b=(new String(s1).equals(s2));
  	  		
  	  		
  	  		if(b){
  	  			s1=getTokenString(hs, uid, cp,set);
  	  		b=(new String(s1).equals(s2));
  	  		}
  	  		//hs.setAttribute(IGlobalConstants.login_token,corp);
  	  		return b;
  		}catch(Exception e){
  			throw new WiseRunException(e);
  		}
  	}
  	public static String getSessionID(String token)throws DZFWarpException{
  		if(StringUtil.isEmptyWithTrim(token))return null;
  		
  		byte[] bs=getValue(token);
  		if(bs!=null&&bs.length>0){
  		try {
			String[] strs=(String[]) IOUtils.getObject(bs);
			if(strs!=null&&strs.length>1){
				return strs[1];
			}
		} catch (Exception e) {
			throw new WiseRunException(e);
		}
  		}
//  			
//  		token=token.substring(token.lastIndexOf("     "));
//		token=token.trim();
//  		return token;
  		return null;
  	}
  	private static byte[] getValue(String token) {
  		byte[] bs=null;
  		try{
  		//token= new String(
  				bs=RSACoder.decryptByPrivateKey(Base64Util.getFromBASE64(token), RsaKeyCache.getInstance().getPrivateKey());//privateKey);
			//bs=Base64Util.getFromBASE64(token);
  		}catch(Exception e){
  			throw new WiseRunException(e);
  		}
  		return bs;
  	}
	private static String getToken(HttpSession hs, String userid, String corp,HashSet<Integer> set)
			throws Exception {
		
//		StringBuffer sb=new StringBuffer();
//		sb.append(userid).append("    ").append(corp);
//		sb.append("     ").append(hs.getId());
		corp= Base64Util.getBASE64(RSACoder.encryptByPublicKey(getTokenString(hs,userid,corp,set), RsaKeyCache.getInstance().getPublicKey()));//publicKey));
		return corp;
	}
	private static byte[] getTokenString(HttpSession hs, String userid, String corp,HashSet<Integer> set)
			throws Exception {
		
//		String str=MD516.Md5(IOUtils.getBytes(set));
		StringBuffer sbSet = new StringBuffer();
		Integer[] is = set.toArray(new Integer[0]);
		Arrays.sort(is);
		for (Integer i : is)
		{
			sbSet.append(String.valueOf(i));
		}
		String str = MD516.Md5(sbSet.toString().getBytes());

		str = MD516.Md5(IOUtils.getBytes(new String[]{userid, corp, str}));//new String(IOUtils.getBytes(set)));
		
		return IOUtils.getBytes(new String[]{str,hs.getId()});
	}

}
