package com.dzf.pub.framework.rsa;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dzf.framework.comn.IOUtils;
import com.dzf.pub.BusinessException;
import com.dzf.pub.IGlobalConstants;
import com.dzf.pub.StringUtil;


public class RSACoderUtils {

	public RSACoderUtils() {
		// TODO Auto-generated constructor stub
	}
    private static String publicKey;  
    private static String privateKey;  

    static {  
       

        try {
        	 Map<String, Object> keyMap = RSACoder.initKey();  
			publicKey = RSACoder.getPublicKey(keyMap);
			privateKey = RSACoder.getPrivateKey(keyMap); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
}

  //加密
  	public static void createToken(HttpSession hs){
  		try{
  			String userid=(String) hs.getAttribute(IGlobalConstants.login_user);
  	  		String corp=(String) hs.getAttribute(IGlobalConstants.login_corp);
  	  		corp = getToken(hs, userid, corp);  
  	  		hs.setAttribute(IGlobalConstants.login_token,corp);
  		}catch(Exception e){
  			throw new BusinessException(e.getMessage());
  		}
  		
  	}
  	//加密
  	public static boolean validateToken(HttpSession hs,String uid,String cp){
  		try{
  			String userid=(String) hs.getAttribute(IGlobalConstants.login_user);
  	  		String corp=(String) hs.getAttribute(IGlobalConstants.login_corp);
  	  		byte[] s1 = getTokenString(hs, userid, corp);
  	  		String s2=(String) hs.getAttribute(IGlobalConstants.login_token);
  	  		byte[] s3=getValue(s2);
  	  		s2=new String(s3);
  	  		s3=null;
  	  		boolean b=(new String(s1).equals(s2));
  	  		if(b){
  	  			s1=getTokenString(hs, uid, cp);
  	  		b=(new String(s1).equals(s2));
  	  		}
  	  		//hs.setAttribute(IGlobalConstants.login_token,corp);
  	  		return b;
  		}catch(Exception e){
  			throw new BusinessException(e.getMessage());
  		}
  	}
  	public static String getSessionID(String token)throws BusinessException{
  		if(StringUtil.isEmptyWithTrim(token))return null;
  		
  		byte[] bs=getValue(token);
  		if(bs!=null&&bs.length>0){
  		try {
			String[] strs=(String[]) IOUtils.getObject(bs);
			if(strs!=null&&strs.length>2){
				return strs[2];
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
  				bs=RSACoder.decryptByPrivateKey(Base64Util.getFromBASE64(token), privateKey);
			//bs=Base64Util.getFromBASE64(token);
  		}catch(Exception e){
  			throw new BusinessException(e);
  		}
  		return bs;
  	}
	private static String getToken(HttpSession hs, String userid, String corp)
			throws Exception {
		
//		StringBuffer sb=new StringBuffer();
//		sb.append(userid).append("    ").append(corp);
//		sb.append("     ").append(hs.getId());
		corp= Base64Util.getBASE64(RSACoder.encryptByPublicKey(getTokenString(hs,userid,corp), publicKey));
		return corp;
	}
	private static byte[] getTokenString(HttpSession hs, String userid, String corp)
			throws Exception {
		
//		StringBuffer sb=new StringBuffer();
//		sb.append(userid).append("    ").append(corp);
//		sb.append("     ").append(hs.getId());
//		return sb.toString();
		return IOUtils.getBytes(new String[]{userid,corp,hs.getId()});
	}
	public static void test(){
		StringBuffer sb=new StringBuffer();
		sb.append("sfsdfsdfsfs");
		String str;
		try {
			str = Base64Util.getBASE64(RSACoder.encryptByPublicKey(Base64Util.getBASE64(sb.toString()).getBytes(), publicKey));
			str= new String(RSACoder.decryptByPrivateKey(Base64Util.getFromBASE64(str), privateKey));
	  		str=new String(Base64Util.getFromBASE64(str));
	  		str=str.substring(str.lastIndexOf("     "));
	  		str=str.trim();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
