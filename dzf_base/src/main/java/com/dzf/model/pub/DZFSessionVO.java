package com.dzf.model.pub;

import com.dzf.pub.SuperVO;
import com.dzf.pub.session.DZFSession;

public class DZFSessionVO extends SuperVO {
	
	private DZFSession[] sessions = null;
	
	public DZFSessionVO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public DZFSession[] getSessions() {
		return sessions;
	}

	public void setSessions(DZFSession[] sessions) {
		this.sessions = sessions;
	}

}
