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
 *         鏁版嵁搴撹闂府鍔╃被灏佽浜嗗父鐢ㄧ殑鎸佷箙灞傝闂搷浣�
 */

final public class BaseDAO {
	private static String[] tablenames={"bdcurrtype","yntcpcosttransvo","yntremittance","ynt_tdaccschema"};
	private DataSource dataSource = null;
	
//	private Logger log = Logger.getLogger(this.getClass());

	int maxRows = 100000;

	boolean addTimestamp = true;

	/**
	 * 鏈夊弬鏋勯�鍑芥暟锛屽皢浣跨敤鎸囧畾鏁版嵁婧�
	 * 
	 * @param dataSource
	 *            鏁版嵁婧愬悕绉�
	 */
	public BaseDAO(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

//	/**
//	 * 鏍规嵁SQL 鎵ц鏁版嵁搴撴煡璇�骞惰繑鍥濺esultSetProcessor澶勭悊鍚庣殑瀵硅薄 锛堥潪 Javadoc锛�
//	 * 
//	 * @param sql
//	 *            鏌ヨ鐨凷QL
//	 * @param processor
//	 *            缁撴灉闆嗗鐞嗗櫒
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
	 * 鏍规嵁鎸囧畾SQL 鎵ц鏈夊弬鏁扮殑鏁版嵁搴撴煡璇�骞惰繑鍥濺esultSetProcessor澶勭悊鍚庣殑瀵硅薄
	 * 
	 * @param sql
	 *            鏌ヨ鐨凷QL
	 * @param parameter
	 *            鏌ヨ鍙傛暟
	 * @param processor
	 *            缁撴灉闆嗗鐞嗗櫒
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
	 * 鏍规嵁鎸囧畾SQL 鎵ц鏈夊弬鏁扮殑鏁版嵁搴撴洿鏂版搷浣�
	 * 
	 * @param sql
	 *            鏇存柊鐨剆ql
	 * @param parameter
	 *            鏇存柊鍙傛暟
	 * @return
	 * @throws DAOException
	 *             鏇存柊鍙戠敓閿欒鎶涘嚭DAOException
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
	 * 鏍规嵁鎸囧畾SQL 鎵ц鏃犲弬鏁扮殑鏁版嵁搴撴洿鏂版搷浣�
	 * 
	 * @param sql
	 *            鏇存柊鐨剆ql
	 * @return
	 * @throws DAOException
	 *             鏇存柊鍙戠敓閿欒鎶涘嚭DAOException
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
	 * 鏍规嵁VO绫诲悕鏌ヨ璇O瀵瑰簲琛ㄧ殑鎵�湁鏁版嵁
	 * 
	 * @param className
	 *            SuperVo绫诲悕
	 * 
	 * @return
	 * @throws DAOException
	 *             鍙戠敓閿欒鎶涘嚭DAOException
	 */
	public Collection retrieveAll(Class className) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			SuperVO svo=(SuperVO) className.newInstance();
			String tabname=svo.getTableName();
			int index=Arrays.binarySearch(tablenames, tabname.toLowerCase());
			if(index<0)throw new DAOException("璇ヨ〃涓嶅叿鏈夊叏琛ㄦ煡璇㈡潯浠�);
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
	 * 閫氳繃where鏉′欢鏌ヨ鎵�湁婊¤冻鏉′欢鐨剉o鏁扮粍銆�鏀寔澶氳〃 鍒涘缓鏃ユ湡锛�2002-6-12)
	 * 
	 * @param c
	 *            Class
	 * @param strWhere
	 *            String
	 * @return SuperVO[]
	 * @throws Exception
	 *             寮傚父璇存槑銆�
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
	 * 鏍规嵁VO涓殑灞炴�鍊间綔涓哄尮閰嶆潯浠朵粠鏁版嵁搴撲腑鏌ヨ璇O瀵瑰簲鐨勮〃鐨勬暟鎹�
	 * 
	 * @param vo
	 *            瑕佹煡璇㈢殑VO瀵硅薄
	 * @param isAnd
	 *            鎸囧畾鍖归厤鏉′欢鐨勯�杈戯紙true浠ｈ〃&&,flase浠ｈ〃||锛�
	 * @return
	 * @throws DAOException
	 *             濡傛灉鏌ヨ鍑洪敊鎶涘嚭DAOException
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
	 * 鏍规嵁鎸囧畾VO鐨勫�浠ュ強閫昏緫鏉′欢杩斿洖鎸囧畾瀛楁鐨刅O闆嗗悎
	 * 
	 * @param vo
	 *            鏉′欢VO
	 * @param isAnd
	 *            閫昏緫鏉′欢锛宼rue浠ｈ〃涓庤繍绠梖alse浠ｈ〃鎴栬繍绠�
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
	 * 鏍规嵁鍏徃鍚嶅拰鎸囧畾瀛楁杩斿洖VO闆嗗悎
	 * 
	 * @param className
	 *            VO绫诲悕
	 * @param pkCorp
	 *            鍏徃涓婚敭
	 * @param selectedFields
	 *            鏌ヨ瀛楁
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
	 * 鏍规嵁鍏徃PK杩斿洖鎸囧畾VO闆嗗悎
	 * 
	 * @param className
	 *            VO鍚嶇О
	 * @param 鍏徃PK
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
	 * 鏍规嵁PK鏌ヨ鎸囧畾VO
	 * 
	 * @param VO绫诲悕
	 * @param pk
	 *            涓婚敭
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
	 * 鏍规嵁涓婚敭杩斿洖鎸囧畾鍒楃殑VO瀵硅薄
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
	 * 鎻掑叆涓�釜VO瀵硅薄锛屽鏋滆VO鐨勪富閿�闈炵┖鍒欐彃鍏O鐨勫師鏈変富閿�
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
	 * 鎻掑叆涓�釜VO瀵硅薄
	 * 
	 * @param vo
	 *            SuperVO瀵硅薄
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
	 * 鎻掑叆涓�釜VO鏁扮粍濡傛灉璇O鐨勪富閿�闈炵┖鍒欐彃鍏O鐨勫師鏈変富閿�
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
	 * 鎻掑叆VO鏁扮粍
	 * 
	 * @param vo
	 *            VO鏁扮粍
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
	 * 璇ユ柟娉曞彧鑳借singleObejctBO璋冪敤
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
	 * 璇ユ柟娉曞彧鑳借singleObejctBO璋冪敤
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
	 * 鎻掑叆VO闆嗗悎
	 * 
	 * @param vos
	 *            VO闆嗗悎
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
	 * 鏍规嵁IMappingMeta鎻掑叆涓�釜VO瀵硅薄锛岃VO鐨勪富閿�闈炵┖鍒欐彃鍏O鐨勫師鏈変富閿�
	 * 
	 * @param vo
	 *            VO瀵硅薄
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
	 * 鏍规嵁IMappingMeta鎻掑叆涓�釜VO瀵硅薄
	 * 
	 * @param vo
	 *            VO瀵硅薄
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
//	 * 鏍规嵁IMappingMeta鎻掑叆VO瀵硅薄闆嗗悎锛岃VO鐨勪富閿�闈炵┖鍒欐彃鍏O鐨勫師鏈変富閿�
//	 * 
//	 * @param vo
//	 *            VO瀵硅薄闆嗗悎
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
//	 * 鏍规嵁IMappingMeta鎻掑叆VO瀵硅薄闆嗗悎
//	 * 
//	 * @param vo
//	 *            VO瀵硅薄闆嗗悎
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
	 * 鏍规嵁VO瀵硅薄鏇存柊鏁版嵁搴�
	 * 
	 * @param vo
	 *            VO瀵硅薄
	 */
	public int updateVO(SuperVO vo) throws DAOException {
		return updateVOArray(new SuperVO[] { vo });
	}

	/**
	 * 鏍规嵁VO瀵硅薄涓寚瀹氬垪鏇存柊鏁版嵁搴�
	 * 
	 * @param vos
	 *            VO瀵硅薄
	 * @param fieldNames
	 *            鎸囧畾鍒�
	 * @throws DAOException
	 */
	public void updateVO(SuperVO vo, String[] fieldNames) throws DAOException {
		updateVOArray(new SuperVO[] { vo }, fieldNames);
	}

	/**
	 * 鏍规嵁VO瀵硅薄鏁扮粍鏇存柊鏁版嵁搴�
	 * 
	 * @param vo
	 *            VO瀵硅薄
	 */
	public int updateVOArray(SuperVO[] vos) throws DAOException {
		return updateVOArray(vos, null);
	}

	/**
	 * 鏍规嵁VO瀵硅薄鏁扮粍涓寚瀹氬垪鏇存柊鏁版嵁搴�
	 * 
	 * @param vos
	 *            VO瀵硅薄
	 * @param fieldNames
	 *            鎸囧畾鍒�
	 */
	public int updateVOArray(SuperVO[] vos, String[] fieldNames)
			throws DAOException {
		return updateVOArray(vos, fieldNames, null, null);

	}

	/**
	 * 鏍规嵁VO瀵硅薄闆嗗悎鏇存柊鏁版嵁搴�
	 * 
	 * @paramvos VO瀵硅薄闆嗗悎
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
	 * 鏍规嵁VO瀵硅薄鍜孧appingMeta淇℃伅鏇存柊鏁版嵁搴�
	 * 
	 * @param vo
	 *            VO瀵硅薄
	 * @param meta
	 *            MappingMeta淇℃伅
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
	 * 鍦ㄦ暟鎹簱涓垹闄や竴缁刅O瀵硅薄銆�
	 * 
	 * @param SuperVO
	 *            [] vos
	 * @throws Exception
	 *             寮傚父璇存槑銆�
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
	 * 鍦ㄦ暟鎹簱涓垹闄や竴缁刅O瀵硅薄銆�
	 * 
	 * @param SuperVO
	 *            [] vos
	 * @throws Exception
	 *             寮傚父璇存槑銆�
	 */
//	public UpdateResultVO execUpdateByVoState(SuperVO[] vos)
//			throws DAOException {
//		return execUpdateByVoState(vos, null);
//	}

	/**
	 * 鍦ㄦ暟鎹簱涓垹闄や竴涓猇O瀵硅薄銆�
	 * 
	 * @param vo
	 *            VO瀵硅薄
	 * @throws DAOException
	 *             濡傛灉鍒犻櫎鍑洪敊鎶涘嚭DAOException
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
	 * 鍦ㄦ暟鎹簱涓垹闄や竴缁刅O瀵硅薄銆�
	 * 
	 * @param vos
	 *            VO鏁扮粍瀵硅薄
	 * @throws DAOException
	 *             濡傛灉鍒犻櫎鍑洪敊鎶涘嚭DAOException
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
	 * 鍦ㄦ暟鎹簱涓牴鎹被鍚嶅拰PK鏁扮粍鍒犻櫎涓�粍VO瀵硅薄闆嗗悎
	 * 
	 * @param className
	 *            瑕佸垹闄ょ殑VO绫诲悕
	 * @param pks
	 *            PK鏁扮粍
	 * @throws DAOException
	 *             濡傛灉鍒犻櫎鍑洪敊鎶涘嚭DAOException
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
	 * 鍦ㄦ暟鎹簱涓牴鎹被鍚嶅拰PK鍒犻櫎涓�釜VO瀵硅薄闆嗗悎
	 * 
	 * @param className
	 *            VO绫诲悕
	 * @param pk
	 *            PK鍊�
	 * @throws DAOException
	 *             濡傛灉鍒犻櫎鍑洪敊鎶涘嚭DAOException
	 */
	public int deleteByPK(Class className, String pk) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			return manager.deleteByPK(className, pk);
		} catch (DbException e) {
			Logger.error(this,e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * 鍦ㄦ暟鎹簱涓垹闄や竴缁刅O瀵硅薄闆嗗悎
	 * 
	 * @param vos
	 *            VO瀵硅薄闆嗗悎
	 * @throws DAOException
	 *             濡傛灉鍒犻櫎鍑洪敊鎶涘嚭DAOException
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
			// ORACLE闇�皢琛ㄥ悕澶у啓
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
