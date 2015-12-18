package com.dzf.framework.comn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.dzf.pub.BusinessException;
import com.dzf.pub.StringUtil;
import com.dzf.pub.SuperVO;
import com.dzf.pub.cache.IDzfSerializable;

public class IOUtils {

	public IOUtils() {
		// TODO Auto-generated constructor stub
	}
public static byte[] getBytes(String[] obj) throws Exception{
	ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
    NetObjectOutputStream nos=	new NetObjectOutputStream(bout);
     
     int len=obj==null?0:obj.length;
     nos.write(len);
    
     for(int i=0;i<len;i++){
    	 if(StringUtil.isEmptyWithTrim(obj[i])){
    	 nos.writeByte(0);
    	// nos.write(obj[i].getBytes());
    	 }else{
    		 nos.writeByte(obj[i].length());
        	 nos.write(obj[i].getBytes());
    	 }
     }
    // nos.writeObject(obj);
		nos.flush();
		nos.close();
		return bout.toByteArray();
}

public static byte[] getBytes(Object obj) throws Exception{
	ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
     NetObjectOutputStream nos=	new NetObjectOutputStream(bout);
     nos.writeObject(obj);
//     int len=obj==null?0:obj.length;
//     nos.write(len);
//     for(int i=0;i<len;i++){
//    	 nos.writeUTF(obj[i]);
//     }
    // nos.writeObject(obj);
		nos.flush();
		nos.close();
		return bout.toByteArray();
}
public static byte[] getBytes(SuperVO svo,IDzfSerializable iser) throws Exception{
	ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
     NetObjectOutputStream nos=	new NetObjectOutputStream(bout);
    iser.setSerializable(svo, nos);

		nos.flush();
		nos.close();
		return bout.toByteArray();
}
public static SuperVO getObject(byte[] bs,IDzfSerializable iser) throws Exception{
	ByteArrayInputStream bin = new ByteArrayInputStream(bs);
	NetObjectInputStream is=new NetObjectInputStream(bin);
	SuperVO svo=(SuperVO) iser.getSerializable(is);
	
	is.close();
	return svo;

}
public static String[] getObject(byte[] bs) throws Exception{
	ByteArrayInputStream bin = new ByteArrayInputStream(bs);
	NetObjectInputStream is=new NetObjectInputStream(bin);
	
	int len=is.read();
	String[] strs=new String[len];
	int len1=0;
	byte[] bs1=null;
	  for(int i=0;i<len;i++){
		  len1=is.readByte();
		  if(len1>0){
			  bs1=new byte[len1];
			  is.read(bs1);
		  strs[i]=new String(bs1);//is.readUTF();
		  }
	     }
	//Object obj= is.readObject();
	is.close();
	return strs;

}

}
