package com.dzf.pub.cache;

import java.io.IOException;

import com.dzf.framework.comn.NetObjectInputStream;
import com.dzf.framework.comn.NetObjectOutputStream;
import com.dzf.pub.lang.DZFBoolean;
import com.dzf.pub.lang.DZFDate;

public abstract class AbstractSerializable<T> implements IDzfSerializable<T> {

	public AbstractSerializable() {
		// TODO Auto-generated constructor stub
	}
	protected void write(NetObjectOutputStream nos,String str) throws IOException{
		int len=str==null?0:str.length();
		nos.writeByte(len);
		if(len>0)
			nos.write(str.getBytes());
	}
	protected void write(NetObjectOutputStream nos,DZFBoolean b) throws IOException{
			if(b==null)b=DZFBoolean.FALSE;
			nos.writeBoolean(b.booleanValue());
			//nos.write(b.toString().getBytes());
	}
	protected void writeByte(NetObjectOutputStream nos,Integer v) throws IOException{
		if(v==null)v=-1;
		
		nos.writeByte(v);
	}
	protected void writeBytes(NetObjectOutputStream nos,DZFBoolean...b) throws IOException{
		//存储7个布尔值
		int len=b==null?0:b.length;
		StringBuffer sb=new StringBuffer(len+1);
		DZFBoolean db=null;
		sb.append(1);
		for (int i = 0; i < len; i++) {
			db=b[i];
			sb.append(db.booleanValue()?1:0);
		}
		nos.writeByte(Byte.parseByte(sb.toString(),2));
		//nos.writeInt(v);
	}

	protected void writeInt(NetObjectOutputStream nos,Integer v) throws IOException{
		if(v==null)v=-1;
		nos.writeInt(v);
	}
	protected void write(NetObjectOutputStream nos,DZFDate b) throws IOException{
		long l=0;
		if(b!=null)l=b.toDate().getTime();
		nos.writeLong(l);
	
}
	protected DZFDate reader(NetObjectInputStream nos) throws IOException{
		long l=nos.readLong();
	return new DZFDate(l);
	
}
	protected DZFBoolean[] readerDZFBooleans(NetObjectInputStream nos) throws IOException{
		int z = nos.readByte();
		z |= 256;
		String str = Integer.toBinaryString(z);
		char[] cs=str.toCharArray();
		z=str==null?0:str.length();
		DZFBoolean[] bs=new DZFBoolean[z-1];
		for (int i = 1; i < z; i++) {
			bs[i-1]=(cs[i]=='1')?DZFBoolean.TRUE:DZFBoolean.FALSE;
		}
		return bs;
	}
	protected DZFBoolean readerDZFBoolean(NetObjectInputStream nos) throws IOException{
	return new DZFBoolean(nos.readBoolean());
	}
	protected String readerString(NetObjectInputStream nos,int len) throws IOException{
		if(len<0)len=nos.readInt();
		byte[] bs=new byte[len];
		if(len>0)
		nos.read(bs);
	return new String(bs);
	}
}
