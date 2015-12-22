package com.dzf.pub.cache_new;

import java.io.IOException;

import com.dzf.framework.comn.NetObjectInputStream;
import com.dzf.framework.comn.NetObjectOutputStream;
import com.dzf.model.sys.sys_power.UserVO;
import com.dzf.pub.lang.DZFBoolean;
import com.dzf.pub.lang.DZFDate;

public class UserSerializable implements IDzfSerializable<UserVO> {

	public UserSerializable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setSerializable(UserVO svo, NetObjectOutputStream nos) throws IOException {
		write(nos,svo.getPk_creatcorp());
		write(nos,svo.getLocked_tag());
		write(nos,svo.getUser_code());
		write(nos,svo.getAble_time());
		write(nos,svo.getDisable_time());
	write(nos,svo.getBappuser());
	write(nos,svo.getIsmanager());
	nos.write(svo.getPk_corp().getBytes());
	write(nos,svo.getPwdparam());
	write(nos,svo.getUser_name());
	nos.writeInt(svo.getIstate());
write(nos,svo.getKeyuser());
	nos.write(svo.getCuserid().getBytes());
	nos.writeInt(svo.getPwdtype());
	write(nos,svo.getPk_tempcorp());
write(nos,svo.getPk_signcorp());
write(nos,svo.getBdata());
write(nos,svo.getBaccount());
write(nos,svo.getCorpnm());
write(nos,svo.getCrtcorp());

	
	
	}

	@Override
	public UserVO getSerializable( NetObjectInputStream nos) throws IOException {
		UserVO svo=new UserVO();
	svo.setPk_creatcorp(readerString(nos, -1));
	svo.setLocked_tag(readerDZFBoolean(nos));
	svo.setUser_code(readerString(nos, -1));
	svo.setAble_time(reader(nos));
	svo.setDisable_time(reader(nos));
	svo.setBappuser(readerDZFBoolean(nos));
	svo.setIsmanager(readerDZFBoolean(nos));
	svo.setPk_corp(readerString(nos,6));
	svo.setPwdparam(readerString(nos, -1));
	svo.setUser_name(readerString(nos, -1));
	svo.setIstate(nos.readInt());
	svo.setKeyuser(readerDZFBoolean(nos));
	svo.setCuserid(readerString(nos,24));
	svo.setPwdtype(nos.readInt());
	svo.setPk_tempcorp(readerString(nos, -1));
	svo.setPk_signcorp(readerString(nos, -1));
	svo.setBdata(readerDZFBoolean(nos));
	svo.setBaccount(readerDZFBoolean(nos));
	svo.setCorpnm(readerString(nos,-1));
	svo.setCrtcorp(readerString(nos,-1));
		return svo;
	}
	private void write(NetObjectOutputStream nos,String str) throws IOException{
		int len=str==null?0:str.length();
		if(len>0)
			nos.write(str.getBytes());
	}
	private void write(NetObjectOutputStream nos,DZFBoolean b) throws IOException{
			if(b==null)b=DZFBoolean.FALSE;
			nos.writeBoolean(b.booleanValue());
			//nos.write(b.toString().getBytes());
	}
	private void write(NetObjectOutputStream nos,DZFDate b) throws IOException{
		long l=0;
		if(b!=null)l=b.toDate().getTime();
		nos.writeLong(l);
	
}
	private DZFDate reader(NetObjectInputStream nos) throws IOException{
		long l=nos.readLong();
	return new DZFDate(l);
	
}
	private DZFBoolean readerDZFBoolean(NetObjectInputStream nos) throws IOException{
	return new DZFBoolean(nos.readBoolean());
	}
	private String readerString(NetObjectInputStream nos,int len) throws IOException{
		if(len<0)len=nos.readInt();
		byte[] bs=new byte[len];
		nos.read(bs);
	return new String(bs);
	}
}
