package com.dzf.dao.bs;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

//import org.apache.log4j.Logger;
import javax.sql.DataSource;

import org.springframework.jdbc.core.ConnectionCallback;

import com.dzf.dao.jdbc.framework.JdbcSession;
import com.dzf.dao.jdbc.framework.PersistenceManager;
import com.dzf.dao.jdbc.framework.SQLParameter;
import com.dzf.dao.jdbc.framework.exception.DbException;
import com.dzf.dao.jdbc.framework.processor.ResultSetProcessor;
import com.dzf.dao.jdbc.framework.util.DBConsts;
import com.dzf.pub.Logger;
import com.dzf.pub.SuperVO;


/**
 * @author hey
 *         <p/>
 *         数据库访问帮助类封装了常用的持久层访问操作
 */

final public class BaseDAO {
	private static String[] tablenames={"bdcurrtype","yntcpcosttransvo","yntremittance","ynt_tdaccschema"};
	private DataSource dataSource = null;
	
//	private Logger log = Logger.getLogger(this.getClass());

	int maxRows = 100000;

	boolean addTimestamp = true;

	/**
	 * 有参构造函数，将使用指定数据源
	 * 
	 * @param dataSource
	 *            数据源名称
	 */
	public BaseDAO(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

//	/**
//	 * 根据SQL 执行数据库查询,并返回ResultSetProcessor处理后的对象 （非 Javadoc）
//	 * 
//	 * @param sql
//	 *            查询的SQL
//	 * @param processor
//	 *            结果集处理器
//	 */
//	public Object executeQuery(String sql, ResultSetProcessor processor)
//			throws DAOException {
//		PersistenceManager manager = null;
//		Object value = null;
//		try {
//			manager = createPersistenceManager(dataSource);
//			JdbcSession session = manager.getJdbcSession();
//			value = session.executeQuery(sql, processor);
//
//		} catch (DbException e) {
//			Logger.error(this,e.getMessage(), e);
//			throw new DAOException(e.getMessage());
//		} finally {
//			if (manager != null)
//				manager.release();
//		}
//		return value;
//	}

	/**
	 * 根据指定SQL 执行有参数的数据库查询,并返回ResultSetProcessor处理后的对象
	 * 
	 * @param sql
	 *            查询的SQL
	 * @param parameter
	 *            查询参数
	 * @param processor
	 *            结果集处理器
	 */
	public Object executeQuery(String sql, SQLParameter parameter,
			ResultSetProcessor processor) throws DAOException {
		PersistenceManager manager = null;
		Object value = null;
		try {
			manager = createPersistenceManager(dataSource);

			JdbcSession session = manager.getJdbcSession();
			value = session.executeQuery(sql, parameter, processor);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return value;
	}

	/**
	 * 根据指定SQL 执行有参数的数据库更新操作
	 * 
	 * @param sql
	 *            更新的sql
	 * @param parameter
	 *            更新参数
	 * @return
	 * @throws DAOException
	 *             更新发生错误抛出DAOException
	 */
	public int executeUpdate(String sql, SQLParameter parameter)
			throws DAOException {
		PersistenceManager manager = null;
		int value;
		try {
			manager = createPersistenceManager(dataSource);
			JdbcSession session = manager.getJdbcSession();
			value = session.executeUpdate(sql, parameter);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return value;
	}

	/**
	 * 根据指定SQL 执行无参数的数据库更新操作
	 * 
	 * @param sql
	 *            更新的sql
	 * @return
	 * @throws DAOException
	 *             更新发生错误抛出DAOException
	 */
//	public int executeUpdate(String sql) throws DAOException {
//		PersistenceManager manager = null;
//		int value;
//		try {
//			manager = createPersistenceManager(dataSource);
//			JdbcSession session = manager.getJdbcSession();
//			value = session.executeUpdate(sql);
//
//		} catch (DbException e) {
//			Logger.error(this,e.getMessage(), e);
//			throw new DAOException(e.getMessage());
//		} finally {
//			if (manager != null)
//				manager.release();
//		}
//		return value;
//	}

	/**
	 * 根据VO类名查询该VO对应表的所有数据
	 * 
	 * @param className
	 *            SuperVo类名
	 * 
	 * @return
	 * @throws DAOException
	 *             发生错误抛出DAOException
	 */
	public Collection retrieveAll(Class className) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			SuperVO svo=(SuperVO) className.newInstance();
			String tabname=svo.getTableName();
			int index=Arrays.binarySearch(tablenames, tabname.toLowerCase());
			if(index<0)throw new DAOException("该表不具有全表查询条件");
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveAll(className);

		} catch (Exception e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	public Collection retrieveByClause(Class className, String condition,
			SQLParameter params) throws DAOException {
		return retrieveByClause(className, condition, (String[]) null, params);
	}



	public Collection retrieveByClause(Class className, String condition,
			String orderBy, SQLParameter params) throws DAOException {
		return retrieveByClause(className, appendOrderBy(condition, orderBy),
				(String[]) null, params);
	}


	public Collection retrieveByClause(Class className, String condition,
			String[] fields, SQLParameter params) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveByClause(className, condition, fields,
					params);
		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}



	public Collection retrieveByClause(Class className, String condition,
			String orderBy, String[] fields, SQLParameter params)
			throws DAOException {
		return retrieveByClause(className, appendOrderBy(condition, orderBy),
				fields, params);
	}

	private String appendOrderBy(String condition, String orderBy) {
		StringBuffer clause = new StringBuffer();
		if (condition != null)
			clause.append(condition);
		if (orderBy != null && condition == null)
			clause.append("ORDER BY ").append(orderBy);
		if (orderBy != null && condition != null) {
			clause.append(" ORDER BY ").append(orderBy);
		}

		return clause.toString();
	}

	/**
	 * 通过where条件查询所有满足条件的vo数组。 支持多表 创建日期：(2002-6-12)
	 * 
	 * @param c
	 *            Class
	 * @param strWhere
	 *            String
	 * @return SuperVO[]
	 * @throws Exception
	 *             异常说明。
	 */
//	@SuppressWarnings("unchecked")
//	public Object[] retrieveByClause(Class c, SqlSupportVO[] sqlvos,
//			String fromStr, String strWhere, String strOrderBy)
//			throws DAOException {
//		if (sqlvos == null || sqlvos.length == 0)
//			throw new NullPointerException("Sqlvos is null;");
//		if (fromStr == null)
//			throw new NullPointerException("fromStr is null;");
//		String[][] fields = new String[2][sqlvos.length];
//		MappingMeta meta = new MappingMeta("", "");
//		for (int i = 0; i < sqlvos.length; i++) {
//			fields[0][i] = sqlvos[i].getSqlSelectField();
//			fields[1][i] = sqlvos[i].getVoAttributeName();
//			meta.addMapping(sqlvos[i].getVoAttributeName(),
//					sqlvos[i].getSqlSelectField());
//		}
//		PersistenceManager manager = null;
//		try {
//			manager = createPersistenceManager(this.dataSource);
//			JdbcSession session = manager.getJdbcSession();
//			StringBuffer sql = new StringBuffer("SELECT ");
//			for (int i = 0; i < fields[0].length; i++) {
//				sql.append(fields[0][i]);
//				if (i != fields[0].length - 1)
//					sql.append(",");
//			}
//			sql.append(" FROM ").append(fromStr);
//
//			// create where sentence
//			if (strWhere != null && strWhere.trim().length() != 0) {
//				sql.append(" WHERE ").append(strWhere);
//			}
//			// create order by sentence
//			if (strOrderBy != null && strOrderBy.trim().length() != 0) {
//				sql.append(" ORDER BY ").append(strOrderBy);
//			}
//			BaseProcessor processor = new BeanMappingListProcessor(c, meta);
//			List result = (List) session
//					.executeQuery(sql.toString(), processor);
//			return result.toArray((Object[]) Array.newInstance(c, 0));
//		} catch (DbException e) {
//			Logger.error(this,e.getMessage(), e);
//			throw new DAOException(e.getMessage());
//
//		} finally {
//			if (manager != null)
//				manager.release();
//		}
//	}

	/**
	 * 根据VO中的属性值作为匹配条件从数据库中查询该VO对应的表的数据
	 * 
	 * @param vo
	 *            要查询的VO对象
	 * @param isAnd
	 *            指定匹配条件的逻辑（true代表&&,flase代表||）
	 * @return
	 * @throws DAOException
	 *             如果查询出错抛出DAOException
	 */
	public Collection retrieve(SuperVO vo, boolean isAnd) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieve(vo, isAnd);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

		return values;
	}

	public Collection retrieveOrderBy(SuperVO vo, boolean isAnd,
			String[] orderbyFields) throws DAOException {

		return retrieve(vo, isAnd, null, orderbyFields);

	}

	public Collection retrieve(SuperVO vo, boolean isAnd, String[] fields,
			String[] orderbyFields) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieve(vo, isAnd, fields, orderbyFields);
		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	/**
	 * 根据指定VO的值以及逻辑条件返回指定字段的VO集合
	 * 
	 * @param vo
	 *            条件VO
	 * @param isAnd
	 *            逻辑条件，true代表与运算false代表或运算
	 * 
	 * 
	 */
	public Collection retrieve(SuperVO vo, boolean isAnd, String[] fields)
			throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieve(vo, isAnd, fields);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	/**
	 * 根据公司名和指定字段返回VO集合
	 * 
	 * @param className
	 *            VO类名
	 * @param pkCorp
	 *            公司主键
	 * @param selectedFields
	 *            查询字段
	 * 
	 */
	public Collection retrieveByCorp(Class className, String pkCorp,
			String[] selectedFields) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);

			values = manager.retrieveByCorp(className, pkCorp, selectedFields);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	/**
	 * 根据公司PK返回指定VO集合
	 * 
	 * @param className
	 *            VO名称
	 * @param 公司PK
	 * 
	 */
	public Collection retrieveByCorp(Class className, String pkCorp)
			throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveByCorp(className, pkCorp);
		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	/**
	 * 根据PK查询指定VO
	 * 
	 * @param VO类名
	 * @param pk
	 *            主键
	 * 
	 */
	public Object retrieveByPK(Class className, String pk) throws DAOException {
		PersistenceManager manager = null;
		Object values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveByPK(className, pk);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	/**
	 * 根据主键返回指定列的VO对象
	 */
	public Object retrieveByPK(Class className, String pk,
			String[] selectedFields) throws DAOException {
		PersistenceManager manager = null;
		Object values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveByPK(className, pk, selectedFields);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	/**
	 * 插入一个VO对象，如果该VO的主键值非空则插入VO的原有主键
	 * 
	 * @param vo
	 * @return
	 * @throws DAOException
	 */
	public String insertVOWithPK(String pk_corp,SuperVO vo) throws DAOException {
		PersistenceManager manager = null;
		String pk = null;
		try {
			manager = createPersistenceManager(dataSource);
			
			pk = manager.insertWithPK(pk_corp,vo);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return pk;
	}

	/**
	 * 插入一个VO对象
	 * 
	 * @param vo
	 *            SuperVO对象
	 * 
	 */
	public String insertVO(String pk_corp,SuperVO vo) throws DAOException {
		PersistenceManager manager = null;
		String pk = null;
		try {
			manager = createPersistenceManager(dataSource);
	
			pk = manager.insert(pk_corp,vo);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return pk;

	}

	/**
	 * 插入一个VO数组如果该VO的主键值非空则插入VO的原有主键
	 * 
	 * @param vo
	 * @return
	 * @throws DAOException
	 */
	public String[] insertVOArrayWithPK(String pk_corp,SuperVO[] vo) throws DAOException {
		PersistenceManager manager = null;
		String pk[] = null;
		try {
			manager = createPersistenceManager(dataSource);
			
			pk = manager.insertWithPK(pk_corp,vo);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return pk;
	}

	/**
	 * 插入VO数组
	 * 
	 * @param vo
	 *            VO数组
	 */
	public String[] insertVOArray(String pk_corp,SuperVO[] vo) throws DAOException {
		PersistenceManager manager = null;
		String pk[] = null;
		try {
			manager = createPersistenceManager(dataSource);

			pk = manager.insert(pk_corp,vo);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return pk;
	}
	/*
	 * 该方法只能被singleObejctBO调用
	 * */
	public SuperVO execute(ConnectionCallback<SuperVO> action) throws Exception{
		PersistenceManager manager = null;
		SuperVO svo = null;
		try {
			manager = createPersistenceManager(dataSource);

			svo = manager.execute(action);// .insert(pk_corp,vo);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return svo;
	}
	/*
	 * 该方法只能被singleObejctBO调用
	 * */
	public String[] insertVOArray(Connection conn,String pk_corp,SuperVO[] vo) throws DAOException {
		PersistenceManager manager = null;
		String pk[] = null;
		try {
			manager = createPersistenceManager(dataSource);

			pk = manager.insert(conn, pk_corp, vo);// .insert(pk_corp,vo);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return pk;
	}
	/**
	 * 插入VO集合
	 * 
	 * @param vos
	 *            VO集合
	 */
	public String[] insertVOList(String pk_corp,List vos) throws DAOException {
		PersistenceManager manager = null;
		String pk[] = null;
		try {
			manager = createPersistenceManager(dataSource);
			;
			pk = manager.insert(pk_corp,vos);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return pk;
	}

	/**
	 * 根据IMappingMeta插入一个VO对象，该VO的主键值非空则插入VO的原有主键
	 * 
	 * @param vo
	 *            VO对象
	 * @param meta
	 *            IMappingMeta
	 * @return
	 * @throws DAOException
	 */
//	public String insertObjectWithPK(Object vo, IMappingMeta meta)
//			throws DAOException {
//
//		PersistenceManager manager = null;
//		try {
//			manager = createPersistenceManager(dataSource);
//			;
//			return manager.insertObjectWithPK(vo, meta);
//
//		} catch (DbException e) {
//			Logger.error(this,e.getMessage(), e);
//			throw new DAOException(e.getMessage());
//		} finally {
//			if (manager != null)
//				manager.release();
//		}
//	}

	/**
	 * 根据IMappingMeta插入一个VO对象
	 * 
	 * @param vo
	 *            VO对象
	 * @param meta
	 *            IMappingMeta
	 */
//	public String insertObject(Object vo, IMappingMeta meta)
//			throws DAOException {
//
//		PersistenceManager manager = null;
//		try {
//			manager = createPersistenceManager(dataSource);
//			;
//			return manager.insertObject(vo, meta);
//
//		} catch (DbException e) {
//			Logger.error(this,e.getMessage(), e);
//			throw new DAOException(e.getMessage());
//		} finally {
//			if (manager != null)
//				manager.release();
//		}
//	}
//
//	/**
//	 * 根据IMappingMeta插入VO对象集合，该VO的主键值非空则插入VO的原有主键
//	 * 
//	 * @param vo
//	 *            VO对象集合
//	 * @param meta
//	 *            IMappingMeta
//	 * @return
//	 * @throws DAOException
//	 */
//	public String[] insertObjectWithPK(Object[] vo, IMappingMeta meta)
//			throws DAOException {
//		PersistenceManager manager = null;
//		try {
//			manager = createPersistenceManager(dataSource);
//			;
//			return manager.insertObjectWithPK(vo, meta);
//		} catch (DbException e) {
//			Logger.error(this,e.getMessage(), e);
//			throw new DAOException(e.getMessage());
//		} finally {
//			if (manager != null)
//				manager.release();
//		}
//	}
//
//	/**
//	 * 根据IMappingMeta插入VO对象集合
//	 * 
//	 * @param vo
//	 *            VO对象集合
//	 * @param meta
//	 *            IMappingMeta
//	 * @return
//	 * @throws DAOException
//	 */
//	public String[] insertObject(Object[] vo, IMappingMeta meta)
//			throws DAOException {
//
//		PersistenceManager manager = null;
//		try {
//			manager = createPersistenceManager(dataSource);
//			return manager.insertObject(vo, meta);
//		} catch (DbException e) {
//			Logger.error(this,e.getMessage(), e);
//			throw new DAOException(e.getMessage());
//		} finally {
//			if (manager != null)
//				manager.release();
//		}
//	}

	/**
	 * 根据VO对象更新数据库
	 * 
	 * @param vo
	 *            VO对象
	 */
	public int updateVO(SuperVO vo) throws DAOException {
		return updateVOArray(new SuperVO[] { vo });
	}

	/**
	 * 根据VO对象中指定列更新数据库
	 * 
	 * @param vos
	 *            VO对象
	 * @param fieldNames
	 *            指定列
	 * @throws DAOException
	 */
	public void updateVO(SuperVO vo, String[] fieldNames) throws DAOException {
		updateVOArray(new SuperVO[] { vo }, fieldNames);
	}

	/**
	 * 根据VO对象数组更新数据库
	 * 
	 * @param vo
	 *            VO对象
	 */
	public int updateVOArray(SuperVO[] vos) throws DAOException {
		return updateVOArray(vos, null);
	}

	/**
	 * 根据VO对象数组中指定列更新数据库
	 * 
	 * @param vos
	 *            VO对象
	 * @param fieldNames
	 *            指定列
	 */
	public int updateVOArray(SuperVO[] vos, String[] fieldNames)
			throws DAOException {
		return updateVOArray(vos, fieldNames, null, null);

	}

	/**
	 * 根据VO对象集合更新数据库
	 * 
	 * @paramvos VO对象集合
	 */
	public void updateVOList(List vos) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.update(vos);
		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * 根据VO对象和MappingMeta信息更新数据库
	 * 
	 * @param vo
	 *            VO对象
	 * @param meta
	 *            MappingMeta信息
	 */
//	public int updateObject(Object vo, IMappingMeta meta) throws DAOException {
//		return updateObject(vo, meta, null);
//	}
//
//	public int updateObject(Object[] vos, IMappingMeta meta)
//			throws DAOException {
//		return updateObject(vos, meta, null);
//	}

	/**
	 * 在数据库中删除一组VO对象。
	 * 
	 * @param SuperVO
	 *            [] vos
	 * @throws Exception
	 *             异常说明。
	 */
//	public UpdateResultVO execUpdateByVoState(SuperVO[] vos,
//			String[] selectedFields) throws DAOException {
//		ArrayList<SuperVO> listInsert = new ArrayList<SuperVO>();
//		ArrayList<SuperVO> listUpdate = new ArrayList<SuperVO>();
//		ArrayList<SuperVO> listDelete = new ArrayList<SuperVO>();
//		for (int i = 0; i < vos.length; i++) {
//			int status = vos[i].getStatus();
//			if (status == dzf.vo.pub.VOStatus.NEW)
//				listInsert.add(vos[i]);
//			else if (status == dzf.vo.pub.VOStatus.UPDATED)
//				listUpdate.add(vos[i]);
//			else if (status == dzf.vo.pub.VOStatus.DELETED)
//				listDelete.add(vos[i]);
//		}
//		UpdateResultVO rsVO = new UpdateResultVO();
//		if (listInsert.size() > 0) {
//			rsVO.setPks(insertVOArray((SuperVO[]) listInsert
//					.toArray(new SuperVO[listInsert.size()])));
//		}
//
//		if (listUpdate.size() > 0) {
//			updateVOArray((SuperVO[]) listUpdate.toArray(new SuperVO[listUpdate
//					.size()]), selectedFields);
//		}
//		if (listDelete.size() > 0) {
//			deleteVOArray((SuperVO[]) listDelete.toArray(new SuperVO[listDelete
//					.size()]));
//		}
//		return rsVO;
//	}

	/**
	 * 在数据库中删除一组VO对象。
	 * 
	 * @param SuperVO
	 *            [] vos
	 * @throws Exception
	 *             异常说明。
	 */
//	public UpdateResultVO execUpdateByVoState(SuperVO[] vos)
//			throws DAOException {
//		return execUpdateByVoState(vos, null);
//	}

	/**
	 * 在数据库中删除一个VO对象。
	 * 
	 * @param vo
	 *            VO对象
	 * @throws DAOException
	 *             如果删除出错抛出DAOException
	 */
	public void deleteVO(SuperVO vo) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.delete(vo);
		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * 在数据库中删除一组VO对象。
	 * 
	 * @param vos
	 *            VO数组对象
	 * @throws DAOException
	 *             如果删除出错抛出DAOException
	 */
	public void deleteVOArray(SuperVO[] vos) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.delete(vos);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * 在数据库中根据类名和PK数组删除一组VO对象集合
	 * 
	 * @param className
	 *            要删除的VO类名
	 * @param pks
	 *            PK数组
	 * @throws DAOException
	 *             如果删除出错抛出DAOException
	 */
	public void deleteByPKs(Class className, String[] pks) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.deleteByPKs(className, pks);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	

	public void deleteByClause(Class className, String wherestr,
			SQLParameter params) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.deleteByClause(className, wherestr, params);
		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * 在数据库中根据类名和PK删除一个VO对象集合
	 * 
	 * @param className
	 *            VO类名
	 * @param pk
	 *            PK值
	 * @throws DAOException
	 *             如果删除出错抛出DAOException
	 */
	public void deleteByPK(Class className, String pk) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.deleteByPK(className, pk);
		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * 在数据库中删除一组VO对象集合
	 * 
	 * @param vos
	 *            VO对象集合
	 * @throws DAOException
	 *             如果删除出错抛出DAOException
	 */
	public void deleteVOList(List vos) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.delete(vos);
		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	


	public int getDBType() {

		return DBConsts.ORACLE;

	}


	protected String getTableName(int dbType, String tableName) {
		String strTn = tableName;
		switch (dbType) {
		case DBConsts.POSTGRESQL:
			strTn = tableName;
			break;
		case DBConsts.ORACLE:
		case DBConsts.OSCAR:
		case DBConsts.DB2:
			// ORACLE需将表名大写
			strTn = tableName.toUpperCase();
			break;
		}
		return strTn;
	}


	public boolean isTableExisted(String tableName) throws DAOException {
		return false;
//		if (tableName == null)
//			throw new NullPointerException("TableName is null!");
//		PersistenceManager manager = null;
//		ResultSet rs = null;
//		try {
//			manager = createPersistenceManager(dataSource);
//			int dbType = manager.getDBType();
//			DatabaseMetaData dbmd = manager.getMetaData();
//			if (dbType == DBConsts.ORACLE || dbType == DBConsts.OSCAR
//					|| dbType == DBConsts.DB2 || dbType == DBConsts.POSTGRESQL
//					|| dbType == DBConsts.GBASE) {
//				rs = dbmd.getTables(manager.getCatalog(), manager.getSchema(),
//						getTableName(dbType, tableName),
//						new String[] { "TABLE" });
//			} else {
//				rs = dbmd.getTables(null, null,
//						getTableName(dbType, tableName),
//						new String[] { "TABLE" });
//			}
//			while (rs.next()) {
//				return true;
//			}
//			return false;
//		} catch (Exception e) {
//			Logger.error(this,e.getMessage(), e);
//			throw new DAOException(e.getMessage());
//		} finally {
//			DBUtil.closeRs(rs);
//			if (manager != null)
//				manager.release();
//		}
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	

	public void setAddTimeStamp(boolean addTimeStamp) {
		this.addTimestamp = addTimeStamp;
	}

	public boolean getAddTimeStamp() {
		return addTimestamp;
	}

	private PersistenceManager createPersistenceManager(DataSource ds)
			throws DbException {
		PersistenceManager manager = PersistenceManager.getInstance(ds);
		manager.setMaxRows(maxRows);
		manager.setAddTimeStamp(addTimestamp);
		return manager;
	}

	public int updateVOArray(final SuperVO[] vos, String[] fieldNames,
			String whereClause, SQLParameter param) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			return manager.update(vos, fieldNames, whereClause, param);

		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}

}
