package com.dzf.pub.util;

import java.math.BigInteger;

import org.apache.log4j.Logger;

public class KmbmUpgrade {

	private final static String SPLIT = "/";
	
	private static Logger log = Logger.getLogger(KmbmUpgrade.class);
	
	public static String getNewCode(String oldcode,String oldrule,String newrule) {
		String newcode = "";
		try {
			String[] odru = oldrule.split(SPLIT);
			String[] newru = newrule.split(SPLIT);
			int startIndex = 0;
		    for(int i=0;i<odru.length;i++){
		    	int codelen = new BigInteger(String.valueOf(odru[i])).intValue();
		    	String oldpartCode = oldcode.substring(startIndex, startIndex+codelen);
		    	startIndex+=codelen;
		    	String newpartCode = getNewPartCode(newru[i],oldpartCode);
		    	newcode+=newpartCode;
		    	if(startIndex==oldcode.trim().length()){
		    		break;
		    	}
		    }
		} catch (Exception e) {
			log.error(e);
		}
		return newcode;
	}
	
	private static String getNewPartCode(String newcodeRulePart,String oldpartCode){
		String newPartCode = oldpartCode;
		int newPartLen = Integer.parseInt(newcodeRulePart);
		int oldPartLen = oldpartCode.trim().length();
		if(oldPartLen==newPartLen){
			return newPartCode;
		}
		for(int i=0;i<(newPartLen-oldPartLen);i++){
			newPartCode = "0" + newPartCode;
		}
		return newPartCode;
	}
	
}
