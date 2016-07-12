package com.dzf.pub.cache;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.dzf.framework.comn.NetObjectInputStream;
import com.dzf.framework.comn.NetObjectOutputStream;
import com.dzf.model.pub.DZFSessionVO;

public class SessionSerializable extends AbstractSerializable<DZFSessionVO> {

	public SessionSerializable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setSerializable(DZFSessionVO sessionvo, NetObjectOutputStream nos) throws IOException {
//		if (sessionvo != null && sessionvo.getSessions() != null)
//		{
//			writeObject(nos, sessionvo.getSessions());
//		}
		
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
		nos.writeLong(sessionvo.getLasttime());
		write(nos, sessionvo.getSessionid());
		write(nos, sessionvo.getAppid());
		write(nos, sessionvo.getUuid());
		
	}

	@Override
	public DZFSessionVO getSerializable(NetObjectInputStream nos) throws IOException, ClassNotFoundException {
		
//		DZFSessionVO vo = null;
//		DZFSession[] sessions = (DZFSession[])readObject(nos);
//		if (sessions != null)
//		{
//			vo = new DZFSessionVO();
//			vo.setSessions(sessions);
//		}
//		return vo;
		
		DZFSessionVO sessionvo = new DZFSessionVO();
		int iDzfMapLen = 0;
		Set<Integer> hsset = null;
		
		sessionvo.setPk_corp(readerString(nos, -1));
		sessionvo.setPk_user(readerString(nos, -1));
		sessionvo.setDate(readerString(nos, -1));
		sessionvo.setRemoteIp(readerString(nos, -1));

		iDzfMapLen = nos.readInt();
		
		if (iDzfMapLen > 0)
		{
			hsset = new HashSet<Integer>();
			for (int j = 0; j < iDzfMapLen; j++)
			{
				hsset.add(nos.readInt());
			}
			sessionvo.setDzfMap(hsset);
		}
		sessionvo.setLasttime(nos.readLong());
		sessionvo.setSessionid(readerString(nos, -1));
		sessionvo.setAppid(readerString(nos, -1));
		sessionvo.setUuid(readerString(nos, -1));
		
		return sessionvo;
	}

}
