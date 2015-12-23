package com.dzf.pub.cache;

import java.io.IOException;

import com.dzf.framework.comn.NetObjectInputStream;
import com.dzf.framework.comn.NetObjectOutputStream;
import com.dzf.model.sys.sys_power.UserVO;
import com.dzf.pub.lang.DZFBoolean;
import com.dzf.pub.lang.DZFDate;

public class UserSerializable extends AbstractSerializable<UserVO> {

	public UserSerializable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setSerializable(UserVO svo, NetObjectOutputStream nos) throws IOException {
		write(nos,svo.getPk_creatcorp());
		
		write(nos,svo.getUser_code());
		write(nos,svo.getAble_time());
		write(nos,svo.getDisable_time());
	writeBytes(nos, svo.getLocked_tag(),svo.getBappuser(),svo.getIsmanager(),svo.getKeyuser(),svo.getBdata(),svo.getBaccount());
	
	nos.write(svo.getPk_corp().getBytes());
	write(nos,svo.getPwdparam());
	write(nos,svo.getUser_name());
	writeByte(nos,svo.getIstate());

	nos.write(svo.getCuserid().getBytes());
	writeByte(nos,svo.getPwdtype());
	write(nos,svo.getPk_tempcorp());
write(nos,svo.getPk_signcorp());

write(nos,svo.getCorpnm());
write(nos,svo.getCrtcorp());

	
	
	}

	@Override
	public UserVO getSerializable( NetObjectInputStream nos) throws IOException {
		UserVO svo=new UserVO();
	svo.setPk_creatcorp(readerString(nos, -1));
	
	svo.setUser_code(readerString(nos, -1));
	svo.setAble_time(reader(nos));
	svo.setDisable_time(reader(nos));
	
	DZFBoolean[] bs=readerDZFBooleans(nos);
	svo.setLocked_tag(bs[0]);
	svo.setBappuser(bs[1]);
	svo.setIsmanager(bs[2]);
	svo.setKeyuser(bs[3]);
	svo.setBdata(bs[4]);
	svo.setBaccount(bs[5]);
	
	svo.setPk_corp(readerString(nos,6));
	svo.setPwdparam(readerString(nos, -1));
	svo.setUser_name(readerString(nos, -1));
	svo.setIstate((int) nos.readByte());
	
	svo.setCuserid(readerString(nos,24));
	svo.setPwdtype((int) nos.readByte());
	svo.setPk_tempcorp(readerString(nos, -1));
	svo.setPk_signcorp(readerString(nos, -1));
	
	svo.setCorpnm(readerString(nos,-1));
	svo.setCrtcorp(readerString(nos,-1));
		return svo;
	}
	
}
