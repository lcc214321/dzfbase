package com.dzf.pub.cache;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.dzf.framework.comn.NetObjectInputStream;
import com.dzf.framework.comn.NetObjectOutputStream;
import com.dzf.model.pub.DZFSessionVO;
import com.dzf.pub.cache.AbstractSerializable;

public class TicketSerializable extends AbstractSerializable<DZFSessionVO> {

	public TicketSerializable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setSerializable(DZFSessionVO sessionvo, NetObjectOutputStream nos) throws IOException {
		
		
		write(nos, sessionvo.getPk_corp());
		write(nos, sessionvo.getPk_user());
		write(nos, sessionvo.getDate());
		write(nos, sessionvo.getRemoteIp());
		Set<Integer> hsset = sessionvo.getDzfMap();
		int iDzfMapLen = (hsset == null ? 0 : hsset.size());
		writeInt(nos, iDzfMapLen);
		if (iDzfMapLen > 0)
		{
			Iterator<Integer> it = hsset.iterator();
			while (it.hasNext())
			{
				writeInt(nos, it.next());
			}
		}
		write(nos, sessionvo.getRemoteIp());
		writeLong(nos,sessionvo.getLasttime());
		write(nos, sessionvo.getSessionid());
		write(nos, sessionvo.getAppid());
		write(nos, sessionvo.getUuid());
	}

	@Override
	public DZFSessionVO getSerializable(NetObjectInputStream nos) throws IOException, ClassNotFoundException {
		

		int iDzfMapLen = 0;
		Set<Integer> hsset = null;
		

		DZFSessionVO sessionvo = new DZFSessionVO();
		sessionvo.setPk_corp(readerString(nos, -1));
		sessionvo.setPk_user(readerString(nos, -1));
		sessionvo.setDate(readerString(nos, -1));
		sessionvo.setRemoteIp(readerString(nos, -1));

		iDzfMapLen = readToInt(nos, -1);
		
		if (iDzfMapLen > 0)
		{
			hsset = new HashSet();
			for (int j = 0; j < iDzfMapLen; j++)
			{
				hsset.add(readToInt(nos, -1));
			}
			sessionvo.setDzfMap(hsset);
		}
		sessionvo.setRemoteIp(readerString(nos, -1));
		sessionvo.setLasttime(readLong(nos, -1));
		sessionvo.setSessionid(readerString(nos, -1));
		sessionvo.setAppid(readerString(nos, -1));
		sessionvo.setUuid(readerString(nos, -1));
			
		
		return sessionvo;
	}

}
