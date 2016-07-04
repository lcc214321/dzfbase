package com.dzf.pub.cache;

import java.io.IOException;
import com.dzf.framework.comn.NetObjectInputStream;
import com.dzf.framework.comn.NetObjectOutputStream;
import com.dzf.model.pub.RsaKeyVO;

public class RsaKeySerializable extends AbstractSerializable<RsaKeyVO> {

	public RsaKeySerializable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setSerializable(RsaKeyVO keyvo, NetObjectOutputStream nos) throws IOException {
//		writeObject(nos, keyvo);
		writeLongString(nos, keyvo.getPublicKey());
		writeLongString(nos, keyvo.getPrivateKey());
	}

	@Override
	public RsaKeyVO getSerializable(NetObjectInputStream nos) throws IOException, ClassNotFoundException {

		RsaKeyVO reskeyvo = new RsaKeyVO();
		
		reskeyvo.setPublicKey(readLongString(nos, -1));
		reskeyvo.setPrivateKey(readLongString(nos, -1));
		
		return reskeyvo;
		
//		return (RsaKeyVO)readObject(nos);
	}

}
