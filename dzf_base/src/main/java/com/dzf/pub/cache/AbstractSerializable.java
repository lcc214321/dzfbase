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
		int len=0;
		byte[] bs=new byte[0];
		if(str!=null){
		 bs=str.getBytes("utf-8");
		 len=bs.length;
		}
		nos.writeByte(len);
		if(len>0)
			nos.write(bs);
	}
	/**
	 * 与readLongString配对使用
	 * @param nos
	 * @param str
	 * @throws IOException
	 */
	protected void writeLongString(NetObjectOutputStream nos, String str) throws IOException {
		int len = 0;
		byte[] bs = new byte[0];
		if (str != null) {
			bs = str.getBytes();
			len = bs.length;
		}
		nos.writeInt(len);
		if (len > 0)
			nos.write(bs);
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
		int len=b==null?0:b.length;
		StringBuffer sb=new StringBuffer(len+1);
		DZFBoolean db=null;
		sb.append(1);
		for (int i = 0; i < len; i++) {
			db=b[i];
			sb.append(db!=null&&db.booleanValue()?1:0);
		}
		 long bb= Long.parseLong(sb.toString(),2);//二进制转10进制
		 nos.writeLong(bb);
	    //   String hex = Integer.toBinaryString(bb);
		// byte bc=(byte)bb;
		//nos.writeByte(bc);///byte的取值范围为-128~127
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
		if(l == 0){
			return null;
		}
	return new DZFDate(l);
	
}
	protected DZFBoolean[] readerDZFBooleans(NetObjectInputStream nos) throws IOException{
		long l1 = nos.readLong();		
		String str = Long.toBinaryString(l1);//10进制转2进制度
		char[] cs=str.toCharArray();
		int z=str==null?0:str.length();
		DZFBoolean[] bs=new DZFBoolean[z-1];
		for (int i = 1; i < z; i++) {
			bs[i-1]=(cs[i]=='1')?DZFBoolean.TRUE:DZFBoolean.FALSE;
		}
		return bs;
	}
	protected DZFBoolean readerDZFBoolean(NetObjectInputStream nos) throws IOException{
	return new DZFBoolean(nos.readBoolean());
	}

	protected String readerString(NetObjectInputStream nos, int len) throws IOException {
		if (len < 0)
			len = nos.readByte();
		byte[] bs = new byte[len];
		if (len > 0)
			nos.read(bs);
		return len > 0 ? new String(bs, "utf-8") : null;
	}
	/**
	 * 与writeLongString配对
	 * @param nos
	 * @param len
	 * @return
	 * @throws IOException
	 */
	protected String readLongString(NetObjectInputStream nos, int len) throws IOException {
		if (len < 0)
			len = nos.readInt();
		byte[] bs = new byte[len];
		if (len > 0)
			nos.read(bs);
		return len > 0 ? new String(bs) : null;
	}

}
