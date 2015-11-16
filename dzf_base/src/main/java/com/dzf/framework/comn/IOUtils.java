package com.dzf.framework.comn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

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
    	 nos.writeUTF(obj[i]);
     }
    // nos.writeObject(obj);
		nos.flush();
		nos.close();
		return bout.toByteArray();
}
public static String[] getObject(byte[] bs) throws Exception{
	ByteArrayInputStream bin = new ByteArrayInputStream(bs);
	NetObjectInputStream is=new NetObjectInputStream(bin);
	
	int len=is.read();
	String[] strs=new String[len];
	  for(int i=0;i<len;i++){
		  strs[i]=is.readUTF();
	    	
	     }
	//Object obj= is.readObject();
	is.close();
	return strs;

}
}
