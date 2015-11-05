package com.dzf.service.pub;

import java.util.List;

import com.dzf.dao.jdbc.framework.SQLParameter;
import com.dzf.model.pub.ConditionVO;
import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.model.sys.sys_power.UserVO;
import com.dzf.pub.BusinessException;
import com.dzf.pub.SuperVO;


public interface IBgPubServices {
	//分页查询
	public<T>  List<T> quyerInfovo(Class className,String tableName, String condition,
			SQLParameter params,int pageNo, int pageSize,String order)  throws BusinessException ;
	
	//不分页查询
	public<T> List<T> quyerInfovo(Class className,CorpVO corpVo,
			UserVO uservo,String sort,String order)  throws BusinessException ;
	
	//条件查询
	public <T>  List<T> quyerWithCondtion(Class className,ConditionVO[] cd,String sort,String order)  throws BusinessException ;
	
	//删除
	public void delteInfovo(SuperVO bean)  throws BusinessException ;
	
	//获取总行数
	public int getTotalRow(String tablename,String condition,SQLParameter sp)  throws BusinessException ;
	
	//新增保存
	public<T> T saveNew(SuperVO vo) throws BusinessException;

	//更新
	public void update(SuperVO vo) throws BusinessException;
	
	//按字段更新
	public void updateByColumn(SuperVO vo,String[] columns) throws BusinessException;

	/**
	 * 根据公司查询
	 * @param pk_corp
	 * @return
	 */
	public<T> List<T> quyerByPkcorp(Class className,String pk_corp)throws BusinessException ;


}
