package com.dzf.pub.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.pub.StringUtil;

public class KmbmUpgrade {

	private final static String SPLIT = "/";
	
	private static Logger log = Logger.getLogger(KmbmUpgrade.class);
	
	/**
	 * 返回科目升级后科目编码的Map ///   4/2/2/2/2
	 * 
	 * Map<key,value>-----------key-----升级后的科目编码
	 * 				 -----------value---升级前的科目编码
	 */
	public static Map<String,String> getKmUpgradeinfo(CorpVO vo,String[] vos){
		if(vo == null||vos==null||vos.length ==0)
			return null;
		Map<String,String> updatemap = new HashMap<String,String>();
		String rule = vo.getAccountcoderule();
		boolean flag = false;
		if(!StringUtil.isEmpty(rule)//非空
				&& !rule.startsWith("4/2/2")){//非4/2/2开头
			flag = true;
		}
		for(String code : vos){
			if(StringUtil.isEmpty(code))
				continue;
			if(flag){//科目编码规则存在升级
				String newcode = getNewCode(code,"4/2/2/2/2",rule);
				if(!StringUtil.isEmpty(newcode)){
					updatemap.put(newcode, code);
				}
			}else{
				updatemap.put(code, code);
			}
		}
		return updatemap;
	}
	
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
