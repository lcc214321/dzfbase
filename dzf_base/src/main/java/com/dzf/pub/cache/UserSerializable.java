package com.dzf.pub.cache;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.dzf.framework.comn.NetObjectInputStream;
import com.dzf.framework.comn.NetObjectOutputStream;
import com.dzf.model.sys.sys_power.UserVO;
import com.dzf.pub.lang.DZFBoolean;

public class UserSerializable extends AbstractSerializable<UserVO> {

	private Logger log = Logger.getLogger(this.getClass());
	public UserSerializable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setSerializable(UserVO svo, NetObjectOutputStream nos) throws IOException {
		write(nos,svo.getPk_creatcorp());
		
		write(nos,svo.getUser_code());
		writeDate(nos,svo.getAble_time());
		writeDate(nos,svo.getDisable_time());
	writeBooleans(nos, svo.getLocked_tag(),svo.getBappuser(),svo.getIsmanager(),svo.getKeyuser(),svo.getBdata(),svo.getBaccount(),svo.getIslogin());
	
	write(nos,svo.getPk_corp());
	write(nos,svo.getPwdparam());
	write(nos,svo.getUser_name());
	writeInt(nos,svo.getIstate());

	write(nos,svo.getCuserid());
	writeInt(nos,svo.getPwdtype());
	write(nos,svo.getPk_tempcorp());
write(nos,svo.getPk_signcorp());

write(nos,svo.getCorpnm());
write(nos,svo.getCrtcorp());
write(nos,svo.getPhone());
write(nos, svo.getSkin());
write(nos, svo.getKjskin());
write(nos, svo.getXsstyle());
write(nos, svo.getXsauthor());
write(nos, svo.getMandq());
	
	}

	@Override
	public UserVO getSerializable( NetObjectInputStream nos) throws IOException {
		UserVO svo=new UserVO();
	svo.setPk_creatcorp(readerString(nos, -1));
	
	svo.setUser_code(readerString(nos, -1));
	svo.setAble_time(readDate(nos));
	svo.setDisable_time(readDate(nos));
	
	DZFBoolean[] bs=readerDZFBooleans(nos);
	svo.setLocked_tag(bs[0]);
	svo.setBappuser(bs[1]);
	svo.setIsmanager(bs[2]);
	svo.setKeyuser(bs[3]);
	svo.setBdata(bs[4]);
	svo.setBaccount(bs[5]);
	svo.setIslogin(bs[6]);
	
	svo.setPk_corp(readerString(nos,-1));
	svo.setPwdparam(readerString(nos, -1));
	//log.info("用户字节数："+nos.readByte());
	svo.setUser_name(readerString(nos, -1));
	svo.setIstate(readToInt(nos, -1));
	
	svo.setCuserid(readerString(nos,-1));
	svo.setPwdtype(readToInt(nos, -1));
	svo.setPk_tempcorp(readerString(nos, -1));
	svo.setPk_signcorp(readerString(nos, -1));
	
	svo.setCorpnm(readerString(nos,-1));
	svo.setCrtcorp(readerString(nos,-1));
	svo.setPhone(readerString(nos,-1));
	svo.setSkin(readerString(nos, -1));
	svo.setKjskin(readerString(nos, -1));
	svo.setXsstyle(readerString(nos, -1));
	svo.setXsauthor(readerString(nos, -1));
	svo.setMandq(readerString(nos, -1));

	
		return svo;
	}
	
}
