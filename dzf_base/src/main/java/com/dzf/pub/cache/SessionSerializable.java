package com.dzf.pub.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.dzf.framework.comn.NetObjectInputStream;
import com.dzf.framework.comn.NetObjectOutputStream;
import com.dzf.model.pub.DZFSessionListVO;
import com.dzf.model.pub.DZFSessionVO;

public class SessionSerializable extends AbstractSerializable<DZFSessionListVO> {

	public SessionSerializable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setSerializable(DZFSessionListVO sessionlistvo, NetObjectOutputStream nos) throws IOException {
		List<DZFSessionVO> listDzfSessionVo = sessionlistvo.getListSessionVO();
		
		writeInt(nos, listDzfSessionVo.size());
		
		for (DZFSessionVO sessionvo : listDzfSessionVo)
		{
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
			writeLong(nos,sessionvo.getLasttime());
			write(nos, sessionvo.getSessionid());
			write(nos, sessionvo.getAppid());
			write(nos, sessionvo.getUuid());
			write(nos, sessionvo.getClientid());
		}
	}

	@Override
	public DZFSessionListVO getSerializable(NetObjectInputStream nos) throws IOException, ClassNotFoundException {
		
		DZFSessionListVO sessionListVO = new DZFSessionListVO();
		int iListLen = readToInt(nos, -1);
		List<DZFSessionVO> listSessionVO = new ArrayList<DZFSessionVO>();
		DZFSessionVO sessionvo = null;
		int iDzfMapLen = 0;
		Set<Integer> hsset = null;
		for (int i = 0; i < iListLen; i++)
		{
			sessionvo = new DZFSessionVO();
			
			
			sessionvo.setPk_corp(readerString(nos, -1));
			sessionvo.setPk_user(readerString(nos, -1));
			sessionvo.setDate(readerString(nos, -1));
			sessionvo.setRemoteIp(readerString(nos, -1));
	
			iDzfMapLen = readToInt(nos, -1);
			
			if (iDzfMapLen > 0)
			{
				hsset = new HashSet<Integer>();
				for (int j = 0; j < iDzfMapLen; j++)
				{
					hsset.add(readToInt(nos, -1));
				}
				sessionvo.setDzfMap(hsset);
			}
			sessionvo.setLasttime(readLong(nos, -1));
			sessionvo.setSessionid(readerString(nos, -1));
			sessionvo.setAppid(readerString(nos, -1));
			sessionvo.setUuid(readerString(nos, -1));
			sessionvo.setClientid(readerString(nos, -1));
			listSessionVO.add(sessionvo);
		}
		sessionListVO.setListSessionVO(listSessionVO);
		return sessionListVO;
	}

}
