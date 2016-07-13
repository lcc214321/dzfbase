package com.dzf.model.pub;

import java.util.List;

import com.dzf.pub.SuperVO;

public class DZFSessionListVO extends SuperVO {

	private List<DZFSessionVO> m_listSessionVO;
	
	public DZFSessionListVO() {
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

	public List<DZFSessionVO> getListSessionVO() {
		return m_listSessionVO;
	}

	public void setListSessionVO(List<DZFSessionVO> m_listSessionVO) {
		this.m_listSessionVO = m_listSessionVO;
	}

}
