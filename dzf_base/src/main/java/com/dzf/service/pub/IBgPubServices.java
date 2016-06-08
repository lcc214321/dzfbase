package com.dzf.service.pub;

import java.util.List;

import com.dzf.dao.jdbc.framework.SQLParameter;
import com.dzf.model.pub.ConditionVO;
import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.model.sys.sys_power.UserVO;
import com.dzf.pub.DZFWarpException;
import com.dzf.pub.SuperVO;


public interface IBgPubServices {
	//分页查询
	public<T>  List<T> quyerInfovo(Class className,String tableName, String condition,
			SQLParameter params,int pageNo, int pageSize,String order)  throws DZFWarpException ;
	
	//不分页查询
	public<T> List<T> quyerInfovo(Class className,CorpVO corpVo,
			UserVO uservo,String sort,String order)  throws DZFWarpException ;
	
	//条件查询
	public <T>  List<T> quyerWithCondtion(Class className,ConditionVO[] cd,String sort,String order)  throws DZFWarpException ;
	
	//删除
	public void delteInfovo(SuperVO bean)  throws DZFWarpException ;
	//删除
	public void delteInfovoDzf(SuperVO bean)  throws DZFWarpException ;
	
	//获取总行数
	public int getTotalRow(String tablename,String condition,SQLParameter sp)  throws DZFWarpException ;
	
	//新增保存
	public<T> T saveNew(SuperVO vo) throws DZFWarpException;

	//更新
	public void update(SuperVO vo) throws DZFWarpException;

	//更新
	public void updateDzf(SuperVO vo) throws DZFWarpException;
	
	//按字段更新
	public void updateByColumn(SuperVO vo,String[] columns) throws DZFWarpException;

	/**
	 * 根据公司查询
	 * @param pk_corp
	 * @return
	 */
	public<T> List<T> quyerByPkcorp(Class className,String pk_corp)throws DZFWarpException ;


}
