package com.dzf.framework.comn;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class test {

	public test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try{
			
			
		   ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
		   NetObjectOutputStream dout = new NetObjectOutputStream(bout); 
		    String name = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgCIJjcarbNfmrkFgZE/gvcl6pxPJdUEkmz7PlQMrNfuD0g55a2EM0aT3XT4RoCWeiK8vNBogwFRvOhXHgdPUzOayvz+7hUMyOwwZnmwFQzU3yTTWPb1Qc2Do9rWNO2axhcqIgM5uBpI520JRBeS96qgFzqREMKdnQrMeAsL604QIDAQAB"; 
		    byte[] buff = name.getBytes();
		    dout.writeInt(buff.length);
		    dout.write(buff);
		    buff=null;
		    name="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKAIgmNxqts1+auQWBkT+C9yXqnE"+
"8l1QSSbPs+VAys1+4PSDnlrYQzRpPddPhGgJZ6Iry80GiDAVG86FceB09TM5rK/P7uFQzI7DBmeb"+
"AVDNTfJNNY9vVBzYOj2tY07ZrGFyoiAzm4GkjnbQlEF5L3qqAXOpEQwp2dCsx4CwvrThAgMBAAEC"+
"gYAsVG8Z8OCtPHReq+tbdwamzf2e9WlVo+m/kIV6mcoTxnO2B8eTzpg/Km/w9pvs3b4RVCRuNMMA"+
"3rgHKzvyOzO4e9kxnQssIopTE1PiD+B+HXsGcTrhA80OSyTzMyElC1lH3rjKV+ImShYYu8kbIGxk"+
"ltuQxjLrWu9W+fmQf0qSSQJBAP3ZmUmc+ESKM/Abbk7h5GFeJfy2A0Jv0NyDB2J6QgExaxGfVKhc"+
"oKQSZF0xb75vbVuSXB21u0tjFNsd0Y/nANsCQQChY37mtQ5WJR0QW+hoNIw8M24rMe3VUevOxUCd"+
"JViXov5je8WG86ie1wQDKv4LFF14ZecmrP+eJpwZoTAzuT/zAkEAlP5c7EoHpiL/7RQMoJwRcdzS"+
"qwshyqLakjMN6I0dpJme4E8GX3NOjj6FTfWwQrQgGblD++QotEzJdvp/f9j0NQJAOkJaixoNBNf8"+
"9+zVpthhT1IZkQpDGuCpyUi3nILgykySnIMPuTm4Qy7HSgV5E7LUUyQmH3YQCJ1TeIURi3pyNQJA"+
"Hl3B3UNjATq/wFivLPRP4F85PHOL6wt+rOofjV6TzDN/+cyzjTbXX1UEN0PYi81vXxiE2ElrVZhw"+
"K9AH3Bo8MQ==";
		    buff = name.getBytes();
		    dout.writeInt(buff.length);
		    dout.write(buff);
		    buff=null;
		    dout.flush();
		    dout.close();
		      buff = bout.toByteArray(); 
		    
//		      ByteArrayInputStream bin = new ByteArrayInputStream(buff); 
//		      DataInputStream dis = new DataInputStream(bin); 
//		      String newName = dis.readUTF(); 
//		      int newAge = dis.readInt(); 
//		      //System.out.println(newName+":"+newAge); 
//		      
//		       bout = new ByteArrayOutputStream(); 
//		       NetObjectOutputStream nos=	new NetObjectOutputStream(bout);
//		       nos.writeObject(new String[]{"ddd","eeee"});
//				nos.flush();
//				nos.close();
		      ByteArrayInputStream bin = null;
				 bin = new ByteArrayInputStream(bout.toByteArray());
				 NetObjectInputStream is= new NetObjectInputStream(bin);
				 int len1=is.readInt();
				 buff=new byte[len1];
				is.read(buff);
				String s1=new String(buff);
				len1=is.readInt();
				 buff=new byte[len1];
					is.read(buff);
					String s2=new String(buff);
					buff=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		  
		
		
	
	}

}
