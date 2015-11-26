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
		     DataOutputStream dout = new DataOutputStream(bout); 
		    String name = "fssddfs"; 
		      int age = 84; 
		      dout.writeUTF(name); 
		      dout.writeInt(age); 
		      byte[] buff = bout.toByteArray(); 
		      ByteArrayInputStream bin = new ByteArrayInputStream(buff); 
		      DataInputStream dis = new DataInputStream(bin); 
		      String newName = dis.readUTF(); 
		      int newAge = dis.readInt(); 
		      //System.out.println(newName+":"+newAge); 
		      
		       bout = new ByteArrayOutputStream(); 
		       NetObjectOutputStream nos=	new NetObjectOutputStream(bout);
		       nos.writeObject(new String[]{"ddd","eeee"});
				nos.flush();
				nos.close();
				 bin = new ByteArrayInputStream(bout.toByteArray());
			Object obj= new NetObjectInputStream(bin).readObject();
				if(obj!=null){
					//System.out.println(obj);
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		  
		
		
	
	}

}
