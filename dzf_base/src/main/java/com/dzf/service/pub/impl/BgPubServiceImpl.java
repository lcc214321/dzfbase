package com.dzf.service.pub.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzf.dao.bs.SingleObjectBO;
import com.dzf.dao.jdbc.framework.SQLParameter;
import com.dzf.dao.jdbc.framework.processor.BeanListProcessor;
import com.dzf.model.pub.ConditionVO;
import com.dzf.model.sys.sys_power.CorpVO;
import com.dzf.model.sys.sys_power.UserVO;
import com.dzf.pub.BusinessException;
import com.dzf.pub.SuperVO;
import com.dzf.pub.Field.FieldMapping;
import com.dzf.pub.Field.FieldValidateUtils;
import com.dzf.pub.lang.DZFBoolean;
import com.dzf.service.pub.IBgPubServices;

@Service("i_pubservice")
public class BgPubServiceImpl implements IBgPubServices {

	protected Logger log = Logger.getLogger(this.getClass());
	private SingleObjectBO singleObjectBO;
	
	
	
	public SingleObjectBO getSingleObjectBO() {
		return singleObjectBO;
	}
	@Autowired
	public void setSingleObjectBO(SingleObjectBO singleObjectBO) {
		this.singleObjectBO = singleObjectBO;
	}

	@Override
	public List<SuperVO> quyerInfovo(Class className,String tableName, String condition,
			SQLParameter params,int pageNo, int pageSize,String order) throws BusinessException {
		List<SuperVO>  rs = (List<SuperVO>)singleObjectBO.execQueryWithPage(className, tableName, condition,
				params,pageNo,pageSize,order);
		if(rs == null || rs.size() == 0)
			return null;
		String pk_corp = rs.get(0).getAttributeValue("pk_corp").toString();
		return completinfo(rs,pk_corp);
	}

	
	@Override
	public<T> List<T> quyerInfovo(Class className,CorpVO corpVo,
			UserVO uservo, String sort, String order) throws BusinessException {

		try {
			SuperVO svo = (SuperVO) className.newInstance();

			SQLParameter sp = new SQLParameter();

			StringBuffer sb = new StringBuffer();
			sb.append(" select * from " + svo.getTableName());
			if (corpVo != null) {
				sb.append(" where pk_corp=?");
				sp.addParam(corpVo.getPk_corp());
			}
			if (sort != null) {
				String sortb = FieldMapping.getFieldNameByAlias(svo, sort);
				order = " order by " + (sortb == null ? sort : sortb) + " "
						+ order;
				sb.append(order);
			}
			List<SuperVO> rs = (List<SuperVO>) singleObjectBO.executeQuery(
					sb.toString(), sp, new BeanListProcessor(className));
			if (rs == null || rs.size() == 0)
				return null;
			//String pk_corp = rs.get(0).getAttributeValue("pk_corp").toString();
			
			return completinfo(rs, corpVo.getPk_corp());
			
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	/**
	 * 传入查询条件查询
	 * */
	@Override
	public<T> List<T> quyerWithCondtion(Class className,ConditionVO[] cd,String sort,String order)  throws BusinessException {
		List<SuperVO> rs;
		StringBuffer sb = new StringBuffer();
		SQLParameter sp = new SQLParameter();
		try {

			SuperVO svo = (SuperVO) className.newInstance();
			sb.append(" select * from " + svo.getTableName()+" where nvl(dr,0)=0 ");

			if(cd==null){
				if (sort != null) {
					String sortb = FieldMapping.getFieldNameByAlias(svo, sort);
					order = " order by " + (sortb == null ? sort : sortb) + " "
							+ order;
					sb.append(order);
				}
				rs = (List<SuperVO>)getSingleObjectBO().executeQuery(sb.toString(), sp, new BeanListProcessor(className));
			}else{
				for(ConditionVO pra: cd){
					if(pra==null||pra.getCdname()==null){
						continue;
					}
					if(pra.getCdsymbol()==null){
						sb.append(" and "+pra.getCdname()+" =?");
					}else{
						sb.append(" and"+pra.getCdsymbol());
					}
					sp.addParam(pra.getCdvalue());
				}
				
				if (sort != null) {
					String sortb = FieldMapping.getFieldNameByAlias(svo, sort);
					order = " order by " + (sortb == null ? sort : sortb) + " "
							+ order;
					sb.append(order);
				}
				rs = (List<SuperVO>) singleObjectBO.executeQuery(
						sb.toString(), sp, new BeanListProcessor(className));
				
			}
			if (rs == null || rs.size() == 0)
				return null;
			
			String pk_corp = (String)rs.get(0).getAttributeValue("pk_corp");
			return completinfo(rs, pk_corp);
			
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	
	// 获取总页数
	public int getTotalRow(String sql) throws BusinessException  {
		return singleObjectBO.getTotalRow(sql);
	}

	@Override
	public void delteInfovo(SuperVO bean)  throws BusinessException {
		SuperVO tvo = (SuperVO)bean.clone();
		FieldValidateUtils.Validate(tvo);
		
		singleObjectBO.deleteObject(tvo);
	

	}
	@Override
	public List<SuperVO> quyerByPkcorp(Class className,String pk_corp) throws BusinessException{
			SQLParameter sp=new SQLParameter();
			sp.addParam(pk_corp);
			List<SuperVO> listVo = (List<SuperVO>) singleObjectBO.retrieveByClause(className, "pk_corp=?", sp);
			return completinfo(listVo,pk_corp);
	}
	
	
	@Override
	public SuperVO saveNew(SuperVO vo) throws BusinessException {
		SuperVO tvo =(SuperVO)vo.clone();
		FieldValidateUtils.Validate(tvo);
		
		checkBeforeSaveNew(tvo);
		
		return (SuperVO)singleObjectBO.saveObject((String)tvo.getAttributeValue("pk_corp"), tvo);
	};
	@Override
	public void update(SuperVO vo) throws BusinessException {
		SuperVO tvo =(SuperVO)vo.clone();
		FieldValidateUtils.Validate(tvo);
		
		checkBeforeUpdata(tvo);
		
		singleObjectBO.saveObject((String)tvo.getAttributeValue("pk_corp"), tvo);
	}
	
	
	/**
	 * 按字段更新
	 * **/
	@Override
	public void updateByColumn(SuperVO vo,String[] columns) throws BusinessException {
		if(columns==null||columns.length<=0){
			throw new BusinessException(" 更新字段不可为空");
		}
		SuperVO tvo =(SuperVO)vo.clone();
		FieldValidateUtils.Validate(tvo);
		
		checkBeforeUpdata(tvo);//保存前检查
		
		getSingleObjectBO().update(tvo, columns);// executeUpdate(updatesql, sp);
	}
	
	@Override
	public int getTotalRow(String tablename, String condition, SQLParameter sp)
			throws BusinessException {
		return singleObjectBO.getTotalRow(tablename, condition, sp);
	}

	public<T> List<T> completinfo(List<SuperVO>  rs,String pk_corp) throws BusinessException{
		return (List<T>)rs;
	}
	

	@SuppressWarnings("unchecked")
	public<T> Map<String,T> queryMap(Class className,String pk_corp) throws BusinessException{
		
		Map<String,T> rsmap = new HashMap<String,T>();
		try{
		SQLParameter sp=new SQLParameter();
		sp.addParam(pk_corp);
		
		
		List<T> listVo = (List<T>) getSingleObjectBO().retrieveByClause(className, "pk_corp=?", sp);

		if(listVo != null && listVo.size() > 0){
			for(T pvo : listVo){
				rsmap.put(((SuperVO)pvo).getPrimaryKey(), pvo);
			}
		}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return rsmap;
	}
	
	//protected String errmsg ;
	
	/**
	 * 更新前检查
	 * */
	public DZFBoolean checkBeforeUpdata(SuperVO vo)throws  BusinessException{
		return DZFBoolean.TRUE;
	}
	
	/**
	 * 新增前检查
	 * */
	public DZFBoolean checkBeforeSaveNew(SuperVO vo)throws  BusinessException{
		return DZFBoolean.TRUE;
	}
	
}
