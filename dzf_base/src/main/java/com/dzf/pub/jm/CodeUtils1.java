package com.dzf.pub.jm;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.dzf.pub.WiseRunException;
import com.dzf.pub.DZFWarpException;



/**
 * 数据加密解密
 */
public class CodeUtils1 {
	
	private static ClassPathResource resource = new ClassPathResource("param.txt"); 
	
	private static Logger LOG = Logger.getLogger(CodeUtils1.class);
	
	private static String pubkey;
	
	private static String prikey;
	
	private static String defaultkey;
	
	private static void readUIParameter(){
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			String path = resource.getURL().getPath(); 
			File f = new File(path);
			if (f.isFile()){
				fis = new FileInputStream(f);
				ois = new ObjectInputStream(fis);
				pubkey = (String)ois.readObject();
				prikey =  (String)ois.readObject();
				defaultkey = (String)ois.readObject();
			}
		}catch (Exception ex){
			LOG.error(ex);
			throw new WiseRunException(ex);
		}
	}

	//这里异常吃掉
	public static String enCode(String value) throws DZFWarpException{
		if(pubkey == null || "".equals(pubkey))
			readUIParameter();
		String key = null;
		try{
			key =  RC4.encry_RC4_string(value, defaultkey);
		}catch(Exception e){
			LOG.error(value+"，加密失败",e);
			key = value;
		}
		return key;
	}

	//这里异常吃掉
	public static String deCode(String pvalue) throws DZFWarpException{
		if(prikey == null || "".equals(prikey))
			readUIParameter();
		String key = null;
		try{
			key = RC4.decry_RC4(pvalue,defaultkey);
		}catch(Exception e){
			LOG.error(pvalue+",解密失败",e);
			key = pvalue;
		}
		return key;
	}
}
