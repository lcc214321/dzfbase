package com.dzf.pub.cache;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.dzf.framework.comn.NetObjectInputStream;
import com.dzf.framework.comn.NetObjectOutputStream;
import com.dzf.model.pub.DZFSessionVO;
import com.dzf.pub.session.DZFSession;

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
		DZFSession[] sessionarray = sessionvo.getSessions();
		int iDzfMapLen = 0;
		Set<Integer> hsset = null;
		writeInt(nos, sessionarray.length);
		for (DZFSession session : sessionarray)
		{
			write(nos, session.getPk_corp());
			write(nos, session.getPk_user());
			write(nos, session.getDate());
			write(nos, session.getRemoteIp());
			hsset = session.getDzfMap();
			iDzfMapLen = (hsset == null ? 0 : hsset.size());
			writeInt(nos, iDzfMapLen);
			if (iDzfMapLen > 0)
			{
				Iterator<Integer> it = hsset.iterator();
				while (it.hasNext())
				{
					writeInt(nos, it.next());
				}
			}
			write(nos, session.getRemoteIp());
			nos.writeLong(session.getLasttime());
			write(nos, session.getSessionid());
			write(nos, session.getAppid());
		}
		
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
		
		int arraylen = (int) nos.readInt();
		DZFSession[] sessionarray = new DZFSession[arraylen];
		DZFSession session = null;
		for (int i = 0; i < arraylen; i++)
		{
			session = new DZFSession();
			session.setPk_corp(readerString(nos, -1));
			session.setPk_user(readerString(nos, -1));
			session.setDate(readerString(nos, -1));
			session.setRemoteIp(readerString(nos, -1));

			iDzfMapLen = nos.readInt();
			
			if (iDzfMapLen > 0)
			{
				hsset = new HashSet();
				for (int j = 0; j < iDzfMapLen; j++)
				{
					hsset.add(nos.readInt());
				}
				session.setDzfMap(hsset);
			}
			session.setRemoteIp(readerString(nos, -1));
			session.setLasttime(nos.readLong());
			session.setSessionid(readerString(nos, -1));
			session.setAppid(readerString(nos, -1));
			sessionarray[i] = session;
		}
		sessionvo.setSessions(sessionarray);
		return sessionvo;
	}

}
