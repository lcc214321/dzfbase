/*
 * 创建日期 2005-6-14
 */
package com.dzf.dao.bs;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dzf.dao.jdbc.framework.SQLParameter;
import com.dzf.dao.jdbc.framework.processor.ArrayProcessor;
import com.dzf.dao.jdbc.framework.processor.BeanListProcessor;
import com.dzf.dao.jdbc.framework.processor.ColumnProcessor;
import com.dzf.dao.jdbc.framework.processor.ResultSetProcessor;
import com.dzf.pub.WiseRunException;
import com.dzf.pub.IDGenerate;
import com.dzf.pub.StringUtil;
import com.dzf.pub.SuperVO;
import com.dzf.pub.util.DZfcommonTools;

/**
 * @author wzpeng
 */
@Repository("singleObjectBO")
public class SingleObjectBO {

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public SingleObjectBO() {

	}

	public SingleObjectBO(DataSource d) {
		super();
		this.dataSource = d;
	}

	public void deleteObject(SuperVO svo) throws DAOException {
		String[] keys = null;
		String userid = null;
		int len = 0;

		try {
			BaseDAO dao = new BaseDAO(dataSource);
			SuperVO[] svos = svo.getChildren();
			len = svos == null ? 0 : svos.length;
			StringBuffer buffer = null;
			if (len > 0) {
				buffer = new StringBuffer();
				buffer.append(svos[0].getParentPKFieldName());
				buffer.append("=? ");
				SQLParameter sp = new SQLParameter();
				sp.addParam(svo.getPrimaryKey());
				dao.deleteByClause(svos[0].getClass(), buffer.toString(), sp);
			}
			dao.deleteByPK(svo.getClass(), svo.getPrimaryKey());

		} catch (Exception e) {
			throw new DAOException(e);
		} finally {

		}
	}
	
	
	public void deleteVOArray(SuperVO[] svos) throws DAOException {
		int len = 0;
		try {
			BaseDAO dao = new BaseDAO(dataSource);
			len = svos == null ? 0 : svos.length;
			if (len > 0) {
				dao.deleteVOArray(svos);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {

		}
	}
	public void deleteByPKs(Class className, String[] pks)throws DAOException{
		BaseDAO dao = new BaseDAO(dataSource);
		dao.deleteByPKs(className, pks);
	}
	public int deleteObjectByID(String pk, Class[] cs) throws DAOException {
		String[] keys = null;
		String userid = null;
		int len = 0;

		try {
			BaseDAO dao = new BaseDAO(dataSource);

			len = cs == null ? 0 : cs.length;
			StringBuffer buffer = null;
			if (len > 1) {
				SuperVO svo1 = (SuperVO) cs[1].newInstance();
				buffer = new StringBuffer();
				buffer.append(svo1.getParentPKFieldName());
				buffer.append("=? ");
				SQLParameter sp = new SQLParameter();
				sp.addParam(pk);
				dao.deleteByClause(cs[1], buffer.toString(), sp);
			}
			return dao.deleteByPK(cs[0], pk);

		} catch (Exception e) {
			throw new DAOException(e);
		} finally {

		}
	}
	
	public Collection retrieveByClause(Class className, String condition,
			SQLParameter params) throws DAOException {
		BaseDAO dao = new BaseDAO(dataSource);
		return dao.retrieveByClause(className, condition, params);
	}
	
	
	public Collection retrieveByClause(Class className, String condition,
			String orderBy, SQLParameter params) throws DAOException {
		BaseDAO dao = new BaseDAO(dataSource);
		return dao.retrieveByClause(className, condition,orderBy, params);
	}
	
	public SuperVO[] queryByCondition(Class className, String condition,SQLParameter params) throws DAOException {
		Collection col = retrieveByClause(className, condition, params);
		return (SuperVO[]) col.toArray((SuperVO[]) Array.newInstance(
				className, 0));
	}
	
	
	/**
	 * 根据SQL 执行数据库查询,并返回ResultSetProcessor处理后的对象 （非 Javadoc）
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param processor
	 *            结果集处理器
	 */
	public Object executeQuery(String sql, SQLParameter parameter, ResultSetProcessor processor) throws DAOException {
		BaseDAO dao = new BaseDAO(dataSource);
		return dao.executeQuery(sql, parameter, processor);
	}
	public Object executeQuery(String wheresql, SQLParameter parameter,Class[] cs ) throws DAOException {
		try{
			BeanListProcessor processor1=new BeanListProcessor(cs[0]);
			BaseDAO dao = new BaseDAO(dataSource);
			SuperVO so=(SuperVO) cs[0].newInstance();
			String sql="Select * from "+so.getTableName()+" where "+wheresql;
			List svo= (List) dao.executeQuery(sql, parameter, processor1);
			int len=cs==null?0:cs.length;
			if(len>1&&svo!=null&&svo.size()>0){
				SuperVO so1=(SuperVO) cs[1].newInstance();
				sql="(Select pk_corp,"+so.getPKFieldName()+" f1 from "+so.getTableName()+" where "+wheresql+")";
				sql="select t1.* from "+so1.getTableName()+" t1,"+sql+" t2 where nvl(t1.dr,0) = 0 and t1.pk_corp=t2.pk_corp and t2.f1=t1."+so1.getParentPKFieldName();
				List svos= (List) dao.executeQuery(sql, parameter, new BeanListProcessor(cs[1]));
				
				Map<String, List<SuperVO>> map = DZfcommonTools.hashlizeObject(svos, new String[]{so1.getParentPKFieldName()});//返回MAP信息
				svos=null;
				//len=svo==null?0:svo.length;
				String s=null;
				SuperVO sr=null;
				for(Object x : svo){
					sr=(SuperVO) x;
					s=sr.getPrimaryKey();
					List<SuperVO> liz = map.get(s);
					if(liz != null && !liz.isEmpty()){
						sr.setChildren( DZfcommonTools.convertToSuperVO(liz.toArray(new SuperVO[0])));;
					}
				}
				map=null;
			}
			return svo;
		}catch (Exception e) {
			throw new WiseRunException(e);
		}
	}

	public SuperVO queryVOByID(String pk, Class cs) throws DAOException {
//		try {
//			if (pk == null)
//				return null;
//			else {
//				BaseDAO dao = new BaseDAO(dataSource);
//				SuperVO svo = (SuperVO) dao.retrieveByPK(cs, pk);
//				return svo;
//			}
//
//		} catch (Exception e) {
//			throw new DAOException(e);
//		} finally {
//
//		}
		return this.queryByPrimaryKey(cs, pk);
	}
	
	public SuperVO queryByPrimaryKey( Class cs,String pk) throws DAOException {
		try {
			if (pk == null)
				return null;
			else {
				BaseDAO dao = new BaseDAO(dataSource);
				SuperVO svo = (SuperVO) dao.retrieveByPK(cs, pk);
				return svo;
			}

		} catch (Exception e) {
			throw new DAOException(e);
		} finally {

		}
	}
	
	

	/**
	 * 
	 */
	public SuperVO queryObject(String pkid, Class[] cs)
			throws DAOException {
		try {
			if (pkid == null)
				return null;
			else {
				BaseDAO dao = new BaseDAO(dataSource);
				SuperVO svo = (SuperVO) dao.retrieveByPK(cs[0], pkid);
				Collection cl = null;
				int len = cs == null ? 0 : cs.length;
				StringBuffer buffer = null;
				if (len > 1) {
					SuperVO svo1 = (SuperVO) cs[1].newInstance();
					buffer = new StringBuffer();
					buffer.append(svo1.getParentPKFieldName());
					buffer.append("=? and nvl(dr,0) = 0 ");
					SQLParameter sp = new SQLParameter();
					sp.addParam(pkid);
					cl = dao.retrieveByClause(cs[1], buffer.toString(), sp);
					svo.setChildren((SuperVO[]) cl.toArray(new SuperVO[0]));
				}
				return svo;
			}

		} catch (Exception e) {
			throw new DAOException(e);
		} finally {

		}
	}
	public boolean isExists(String pk_corp, String sql,SQLParameter sp) throws DAOException {

		Object[] objCodeAndDefkey = (Object[])executeQuery(
				"select count(1) from dual where exists(" + sql + ")",
				sp, new ArrayProcessor());

		if (objCodeAndDefkey != null && objCodeAndDefkey.length > 0) {
			BigDecimal i = (BigDecimal) objCodeAndDefkey[0];
			return i.intValue() > 0;
		}
		return false;
	}
public boolean lock(SuperVO svo) throws DAOException{
	boolean b=false;
	try {
		BaseDAO dao = new BaseDAO(dataSource);

		
		StringBuffer buffer = null;
	
			
			buffer = new StringBuffer();
			buffer.append("update ").append(svo.getTableName());
			buffer.append(" set version=").append(svo.getVersion()+1);
			buffer.append(" where ");
			
			buffer.append(svo.getPKFieldName());
			buffer.append("=? ");
			buffer.append(" and version=? ");
			SQLParameter sp = new SQLParameter();
			sp.addParam(svo.getPrimaryKey());
			sp.addParam(svo.getVersion());
			int num=dao.executeUpdate(buffer.toString(), sp);
	b=num>0;
	} catch (Exception e) {
		throw new DAOException(e);
	} finally {

	}
	return b;
}

private SuperVO insertVO(String corp, SuperVO svo){
	int len = 0;
	try {
		String pkid=IDGenerate.getInstance().getNextID(corp);
		svo.setPrimaryKey(pkid);
		BaseDAO dao = new BaseDAO(dataSource);
		
		pkid = dao.insertVOWithPK(corp, svo);
		SuperVO[] svos = svo.getChildren();
		len = svos == null ? 0 : svos.length;
		String[] pks = IDGenerate.getInstance().getNextIDS(corp, len);
		String rField = null;
		if (len > 0)
			rField = svos[0].getParentPKFieldName();
		for (int i = 0; i < len; i++) {
			svos[i].setPrimaryKey(pks[i]);
			svos[i].setAttributeValue(rField, pkid);
		}
		if (len > 0)
			dao.insertVOArray(corp, svos);
		return svo;
	} catch (Exception e) {
		throw new DAOException(e);
	} finally {

	}
	
}

private SuperVO updatevo(String corp, SuperVO svo){
	int len = 0;
	try {
		BaseDAO dao = new BaseDAO(dataSource);
		dao.updateVO(svo);
		String pkid = svo.getPrimaryKey();
		SuperVO[] svos = svo.getChildren();
		len = svos == null ? 0 : svos.length;
		String rField = null;
		if (len > 0){
			List<SuperVO> invo = new ArrayList<SuperVO>();
			List<SuperVO> upvo = new ArrayList<SuperVO>();
			rField = svos[0].getParentPKFieldName();
			for (int i = 0; i < len; i++) {
				String bodypk = svos[i].getPrimaryKey();
				if(StringUtil.isEmpty(bodypk)==false){
					svos[i].setAttributeValue(rField, pkid);
					upvo.add(svos[i]);
				}else{
					String bodyid = IDGenerate.getInstance().getNextID(corp);
					svos[i].setPrimaryKey(bodyid);
					svos[i].setAttributeValue(rField, pkid);
					invo.add(svos[i]);
				}
			}
			if(invo.size() > 0)
				dao.insertVOList(corp, invo);
			if(upvo.size() > 0)
				dao.updateVOList(upvo);
		}
		return svo;
	} catch (Exception e) {
		throw new DAOException(e);
	} finally {

	}
	
}
	/**
	 * 
	 */
	public SuperVO saveObject(String corp, SuperVO svo)
			throws DAOException {

		try{
			String pkid =svo.getPrimaryKey();
			if(StringUtil.isEmpty(pkid)==false){
				return updatevo(corp,svo);
			}else{
				return insertVO(corp,svo);
			}
		}catch(Exception e){
			throw new DAOException(e);
		}finally{
			
		}

//		int len = 0;
//
//		try {
//			String pkid =svo.getPrimaryKey();
//			if(StringUtil.isEmpty(pkid)==false){
//				deleteObject(svo);
//			}else{
//					pkid=IDGenerate.getInstance().getNextID(corp);
//					svo.setPrimaryKey(pkid);
//			}
//					BaseDAO dao = new BaseDAO(dataSource);
//			
//			pkid = dao.insertVOWithPK(corp, svo);
//			SuperVO[] svos = svo.getChildren();
//			len = svos == null ? 0 : svos.length;
//			String[] pks = IDGenerate.getInstance().getNextIDS(corp, len);
//			String rField = null;
//			if (len > 0)
//				rField = svos[0].getParentPKFieldName();
//			for (int i = 0; i < len; i++) {
//				svos[i].setPrimaryKey(pks[i]);
//				svos[i].setAttributeValue(rField, pkid);
//			}
//			if (len > 0)
//				dao.insertVOArray(corp, svos);
//			return svo;
//		} catch (Exception e) {
//			throw new DAOException(e);
//		} finally {
//
//		}
	}
//	public SuperVO saveObject1(final String corp, final SuperVO svo)
//			throws DAOException {
//
//		try {
//			String pkid =svo.getPrimaryKey();
//			if(StringUtil.isEmpty(pkid)==false){
//				deleteObject(svo);
//			}else{
//					pkid=IDGenerate.getInstance().getNextID(corp);
//					svo.setPrimaryKey(pkid);
//			}
//			final BaseDAO dao = new BaseDAO(dataSource);
//			ConnectionCallback<SuperVO> ccb=new ConnectionCallback<SuperVO>() {
//				@Override
//				public SuperVO doInConnection(Connection con) throws SQLException,
//						DataAccessException {
//					try{
//				
//				String[] pks =dao.insertVOArray(corp, new SuperVO[]{svo});
//				String pkid = pks!=null&&pks.length>0?pks[0]:null;
//				SuperVO[] svos = svo.getChildren();
//				int len = svos == null ? 0 : svos.length;
//				pks = IDGenerate.getInstance().getNextIDS(corp, len);
//				String rField = null;
//				if (len > 0)
//					rField = svos[0].getParentPKFieldName();
//				for (int i = 0; i < len; i++) {
//					svos[i].setPrimaryKey(pks[i]);
//					svos[i].setAttributeValue(rField, pkid);
//				}
//				if (len > 0)
//					dao.insertVOArray(corp, svos);
//					}catch(Exception e){
//						throw new SQLException(e);
//					}
//		
//				return svo;
//				}};
//			
//				SuperVO	svo1=dao.execute(ccb);
//					
//			return svo1;
//		} catch (Exception e) {
//			throw new DAOException(e);
//		} finally {
//
//		}
//	}
	/** 分页查询
	 * @param className
	 * @param tableName
	 * @param condition 可以为空 " AND C = ?" 
	 * @param params 可以为空
	 * @param pageNo 
	 * @param pageSize
	 * @param order
	  */
	public List<?> execQueryWithPage(Class className,String tableName, String condition,
			SQLParameter params,int pageNo, int pageSize,String order) throws DAOException {
		
		BaseDAO dao = new BaseDAO(dataSource);
		StringBuffer sb = new StringBuffer();
		if(order!=null){
			sb.append(" select * from ( SELECT ROWNUM AS ROWNO, tt.* FROM ( SELECT t.* FROM ");
		}else{
			sb.append(" select * from ( SELECT ROWNUM AS ROWNO, t.* FROM ");
		}
		sb.append(" "+tableName+" t ");
		sb.append( "WHERE NVL(t.dr,0)=0 ");
		if(condition!=null){
			sb.append(condition);
		}
		if(order!=null){
			sb.append(order+" ) tt WHERE ROWNUM<="+pageNo*pageSize);
		}else{
			sb.append( "AND ROWNUM<="+pageNo*pageSize+" ");
		}
		
		sb.append( " ) WHERE ROWNO> "+(pageNo-1)*pageSize+" ");
		
		return (List<?>)dao.executeQuery(sb.toString(), params, new BeanListProcessor(className));
	}
	
	/**
	 * 获取行数
	 * @param sql ： select count(*) from dual where 1=1
	 * */
	@Deprecated
	public int getTotalRow(String sql) throws DAOException{
		BaseDAO dao = new BaseDAO(dataSource);
		return new Integer(dao.executeQuery(sql, null, new ColumnProcessor()).toString());
	}
	
	public int getTotalRow(String tablename,String Condition, SQLParameter parameter) throws DAOException{
		BaseDAO dao = new BaseDAO(dataSource);
		if(tablename.contains(";")||Condition.contains(";")){
			throw new DAOException("传入表名及参数或含有非法字符");
		}
		String sql = " SELECT COUNT(*) FROM "+tablename+" where nvl(dr,0) = 0 ";
		if(Condition!=null&&Condition.length()>0){
			sql=sql+" and  "+Condition;
		}
		return new Integer(dao.executeQuery(sql, parameter, new ColumnProcessor()).toString());
	}
	
	public int update(SuperVO svo) throws DAOException {
		BaseDAO dao = new BaseDAO(dataSource);
		return dao.updateVO(svo);
	}
	
	public void update(SuperVO svo,String[] fieldNames) throws DAOException {
		BaseDAO dao = new BaseDAO(dataSource);
		dao.updateVO(svo,fieldNames);
	}
	
	
	public int updateAry(SuperVO[] vos) throws DAOException{
		BaseDAO dao = new BaseDAO(dataSource);
		return dao.updateVOArray(vos);
	}
	
	public int updateAry(SuperVO[] vos, String[] fieldNames) throws DAOException{
		BaseDAO dao = new BaseDAO(dataSource);
		return dao.updateVOArray(vos,fieldNames);
	}
	
	public int executeUpdate(String sql, SQLParameter parameter) throws DAOException{
		BaseDAO dao = new BaseDAO(dataSource);
		return dao.executeUpdate(sql, parameter);
	}
	
	public int executeBatchUpdate(String sqls, SQLParameter[] parameters) throws DAOException{
		BaseDAO dao = new BaseDAO(dataSource);
		return dao.executeBatchUpdate(sqls, parameters);
	}
	
	public String[] insertVOArr(String pk_corp,SuperVO[] vos)throws DAOException{
		BaseDAO dao = new BaseDAO(dataSource);
		return dao.insertVOArray(pk_corp,vos);
	}
	
	/**
	 * 插入一个VO对象，如果该VO的主键值非空则插入VO的原有主键
	 * 
	 * @param vo
	 * @return
	 * @throws DAOException
	 */ 
	public String insertVOWithPK(SuperVO vo) throws DAOException {
		BaseDAO dao = new BaseDAO(dataSource);
		return dao.insertVOWithPK(vo.getAttributeValue("pk_corp").toString(), vo);
	}
	public String[] insertVOWithPK(String pk_corp,SuperVO[] vo) throws DAOException {
		BaseDAO dao = new BaseDAO(dataSource);
		return dao.insertVOArrayWithPK(pk_corp, vo);//(vo.getAttributeValue("pk_corp").toString(), vo);
	}
}
