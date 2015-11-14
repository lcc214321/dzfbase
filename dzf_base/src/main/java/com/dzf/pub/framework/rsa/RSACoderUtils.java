package com.dzf.pub.framework.rsa;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dzf.pub.BusinessException;
import com.dzf.pub.IGlobalConstants;


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
  	  		String s1 = getToken(hs, userid, corp);
  	  		boolean b=(corp.equals(hs.getAttribute(IGlobalConstants.login_token)));
  	  		if(b){
  	  			s1=getToken(hs, uid, cp);
  	  		b=(corp.equals(hs.getAttribute(IGlobalConstants.login_token)));
  	  		}
  	  		//hs.setAttribute(IGlobalConstants.login_token,corp);
  	  		return b;
  		}catch(Exception e){
  			throw new BusinessException(e.getMessage());
  		}
  	}
	private static String getToken(HttpSession hs, String userid, String corp)
			throws Exception {
		StringBuffer sb=new StringBuffer();
		sb.append(userid).append("    ").append(corp);
		sb.append("     ").append(hs.getId());
		corp= new String(RSACoder.encryptByPublicKey(sb.toString().getBytes(), publicKey));
		return corp;
	}
}
