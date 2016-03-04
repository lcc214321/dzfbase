package com.dzf.pub.jm;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;



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
			ex.printStackTrace();
		}
	}

	public static String enCode(String value) throws Exception{
		if(pubkey == null || "".equals(pubkey))
			readUIParameter();
		String key = null;
		try{
			key =  RC4.encry_RC4_string(value, defaultkey);
		}catch(Exception e){
			LOG.info(e);
		}
		return key;
	}

	public static String deCode(String pvalue) throws Exception{
		if(prikey == null || "".equals(prikey))
			readUIParameter();
		String key = null;
		try{
			key = RC4.decry_RC4(pvalue,defaultkey);
		}catch(Exception e){
			LOG.info(e);
		}
		return key;
	}
}
