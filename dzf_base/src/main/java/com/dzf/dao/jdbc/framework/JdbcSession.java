package com.dzf.dao.jdbc.framework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dzf.dao.jdbc.framework.exception.DbException;
import com.dzf.dao.jdbc.framework.exception.ExceptionFactory;
import com.dzf.dao.jdbc.framework.processor.ResultSetProcessor;
import com.dzf.dao.jdbc.framework.util.DBConsts;
import com.dzf.dao.jdbc.framework.util.DBUtil;
import com.dzf.pub.SuperVO;


public final class JdbcSession {
	private DataSource dataSource=null;
//	private final int DEFAULT_BATCH_SIZE = 800;

//	private Connection conn = null;

	private int maxRows = 100000;

	private int dbType = DBConsts.ORACLE;

//	private int timeoutInSec = 0;

	private int fetchSize = 40;

	//private PreparedStatement prepStatement = null;

	//private Statement statement = null;

	//private String lastSQL = null;

	//private Batch batch = null;

//	private DatabaseMetaData dbmd = null;

//	private int batchSize = DEFAULT_BATCH_SIZE;

//	private int size = 0;

//	private int batchRows = 0;
	
//	private Logger logger = Logger.getLogger(this.getClass());


	/**
	 * 构造有参数JdbcSession对象
	 * 
	 * @param con
	 *            数据库连接
	 */
//	public JdbcSession(Connection con) {
//
//	//	dbType = DBUtil.getDbType(con);
//		this.conn = con;
//	}

	/**
	 * 构造默认JdbcSession该JdbcSession会默认从当前访问的DataSource得到连接
	 */
	public JdbcSession() throws DbException {
//		try {
//			Connection con = ConnectionFactory.getConnection();
//			dbType = DBUtil.getDbType(con);
//			// dbType = DataSourceCenter.getInstance().getDatabaseType();
//			this.conn = con;
//		} catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		}

	}

	/**
	 * 构造JdbcSession，该JdbcSession会从指定的DataSource中得到连接
	 * 
	 * @param dataSourceName
	 *            数据源名称
	 * @throws DbException
	 *             如果访问数据源出错则抛出DbException
	 */
	public JdbcSession(DataSource dataSourceName) throws DbException {
		this.dataSource=dataSourceName;
//		
//		try {
//			Connection con = ConnectionFactory.getConnection(dataSourceName);
//			dbType=DBConsts.ORACLE;
////			dbType = DataSourceCenter.getInstance().getDatabaseType(
////					dataSourceName);
//
//			this.conn = con;
//		} catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		}

	}

	/**
	 * 设置是否自动添加版本(ts)信息
	 * 
	 * @param isAddTimeStamp
	 */
	public void setAddTimeStamp(boolean isAddTimeStamp) {

	}




//	/**
//	 * 设置自动提交
//	 * 
//	 * @param autoCommit参数
//	 */
//	void setAutoCommit(boolean autoCommit) throws DbException {
//		try {
//			conn.setAutoCommit(autoCommit);
//
//		} catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		}
//	}

	/**
	 * 得到当前连接的FetchSize大小
	 * 
	 * @return int 返回 FetchSize
	 */
	public int getFetchSize() {
		return fetchSize;
	}

	/**
	 * 设置当前连接的FetchSize大小
	 * 
	 * @param fetchSize参数
	 */
	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

//	public int getBatchSize() {
//		return batchSize;
//	}

	/**
	 * 设置批处理大小
	 * 
	 * @param batchSize
	 */
//	public void setBatchSize(int batchSize) {
//		this.batchSize = batchSize;
//	}

	/**
	 * 设置当前连接的事务级别
	 * 
	 * @param level参数
	 */
//	void setTransactionIsolation(int level) throws DbException {
//		try {
//			conn.setTransactionIsolation(level);
//		} catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		}
//	}

	/**
	 * 提交当前连接的事务
	 */
//	void commitTrans() throws DbException {
//		try {
//			conn.commit();
//		} catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		}
//	}

	/**
	 * 回滚当前连接的事务
	 */
//	void rollbackTrans() throws DbException {
//		try {
//			conn.rollback();
//		} catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		}
//	}

	/**
	 * 设置当前连接的只读
	 * 
	 * @param readOnly参数
	 */
//	public void setReadOnly(boolean readOnly) throws DbException {
//		try {
//			conn.setReadOnly(readOnly);
//		} catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		}
//	}

	/**
	 * 当前连接的是否只读
	 * 
	 * @return 返回是否只读
	 */
//	public boolean isReadOnly() throws DbException {
//		try {
//			return conn.isReadOnly();
//		} catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		}
//	}

	/**
	 * 设置执行最大行数
	 * 
	 * @param maxRows
	 */
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	/**
	 * 得到执行最大行数
	 * 
	 * @return
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * 取消查询
	 */
//	public void cancelQuery() throws DbException {
//		try {
//			if (prepStatement != null)
//				prepStatement.cancel();
//		} catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage());
//		}
//	}
//	public List<Map<String, Integer>> executeQuery(String tableName){
//
//	
//		    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
//		    String sql = "select * from "+tableName+" where 1=0";  
//		    List<Map<String, Integer>> list = jdbcTemplate.query(sql,  
//		            new ResultSetExtractor<List<Map<String, Integer>>>() {  
//		  
//		                @Override  
//		                public List<Map<String, Integer>> extractData(  
//		                        ResultSet rs) throws SQLException,  
//		                        DataAccessException {  
//		            		Map<String, Integer> types =new HashMap<String, Integer>();// getColmnTypes(tableName);
//		            		Map<String, Integer> sizes =new HashMap<String, Integer>();// getColmnSize(tableName);
//		                    ResultSetMetaData metaData = rs.getMetaData();  
//		                    int count = metaData.getColumnCount();  
//		                    List<Map<String, Integer>> l = new ArrayList<Map<String,Integer>>();
//		                    for (int i = 0; i < count; i++) {  
////		                        String fieldName = metaData.getColumnName(i + 1).toLowerCase();  
////		                        int type = metaData.getColumnType(i + 1);  
////		                        String typeName = metaData.getColumnTypeName(i + 1).toLowerCase();  
//		                        types.put(metaData.getColumnName(i+1),	metaData.getColumnType(i+1));
//		                        sizes.put(metaData.getColumnName(i+1)
//										.toUpperCase(), metaData.getPrecision(i+1));
//		                    } 
//		                    
//		                    l.add(types);
//		                    l.add(sizes);
//		                    return l;
//		                
//		                }  
//		            });  
//		  
//		 return list;
//	}
	/**
	 * 执行有参数查询
	 * 
	 * @param sql
	 *            查询SQL语句
	 * @param parameter
	 *            查询参数
	 * @param processor
	 *            结果集处理对象
	 * @return 查询对象
	 */
	public Object executeQuery(String sql, final SQLParameter parameter,
			final ResultSetProcessor processor) throws DbException {
		Object result = null;
		//ResultSet rs = null;
		try {
			JdbcTemplate jt=new JdbcTemplate(dataSource);
	
			ResultSetExtractor<Object> rse=new ResultSetExtractor<Object>(){

				@Override
				public Object extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					// TODO Auto-generated method stub
					return processor.handleResultSet(rs);
				}
				
			};
			PreparedStatementSetter pss=new PreparedStatementSetter(){

				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					
					if (parameter != null) {
						DBUtil.setStatementParameter(ps, parameter);
					}
					
				}
				
			};
			jt.setMaxRows(maxRows);
		result=jt.query(sql,pss, rse);
		}catch(Exception e){
			throw ExceptionFactory.getException(1, e.getMessage(), new SQLException(e));
		}
		return result;
		
//		Object result = null;
//		ResultSet rs = null;
//
//		try {
//			if ((!sql.equalsIgnoreCase(lastSQL)) || (prepStatement == null)) {
//				if (prepStatement != null) {
//					closeStmt(prepStatement);
//				}
//				prepStatement = conn.prepareStatement(sql);
//				lastSQL = sql;
//			}
//			prepStatement.clearParameters();
//			if (parameter != null) {
//				DBUtil.setStatementParameter(prepStatement, parameter);
//			}
//			if (timeoutInSec > 0)
//				prepStatement.setQueryTimeout(timeoutInSec);
//
//			prepStatement.setMaxRows(maxRows > 0 ? maxRows : 0);
//			if (fetchSize > 0) {
//				if(maxRows > 0 && fetchSize > maxRows) {
//					fetchSize = maxRows;
//				}
//				prepStatement.setFetchSize(fetchSize);
//			}
//			rs = prepStatement.executeQuery();
//			result = processor.handleResultSet(rs);
//		}
//
//		catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		} catch (NullPointerException e) {
//			SQLException e1 = new SQLException("db connection has interrupted!");
//			throw ExceptionFactory.getException(dbType, e1.getMessage(), e1);
//		} finally {
//			closeRs(rs);
//		}
//		return result;
	}

	/**
	 * 执行无参数查询
	 * 
	 * @param sql
	 *            查询SQL语句
	 * @param processor
	 *            结果集处理对象
	 * @return 查询结果对象
	 */
	public Object executeQuery(String sql, final ResultSetProcessor processor)
			throws DbException {
		
		
		Object result = null;
		//ResultSet rs = null;
		try {
			JdbcTemplate jt=new JdbcTemplate(dataSource);
			ResultSetExtractor<Object> rse=new ResultSetExtractor<Object>(){

				@Override
				public Object extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					// TODO Auto-generated method stub
					return processor.handleResultSet(rs);
				}
				
			};
			jt.setMaxRows(maxRows);
			result=jt.query(sql, rse);
		}catch(Exception e){
			throw ExceptionFactory.getException(1, e.getMessage(), new SQLException(e));
		}
		return result;
			/*if (statement == null)
				statement = conn.createStatement();
			if (timeoutInSec > 0)
				statement.setQueryTimeout(timeoutInSec);

			statement.setMaxRows(maxRows > 0 ? maxRows : 0);

			if (fetchSize > 0) {
				if(maxRows > 0 && fetchSize > maxRows) {
					fetchSize = maxRows;
				}
				statement.setFetchSize(fetchSize);
			}
			rs = statement.executeQuery(sql);
			result = processor.handleResultSet(rs);
		} catch (SQLException e) {
			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
		} catch (NullPointerException e) {
			Logger.error(this,"nullpoint exception", e);
			SQLException e1 = new SQLException(
					"NullPointException cause query error");
			throw ExceptionFactory.getException(dbType, e1.getMessage(), e1);
		} finally {
			closeRs(rs);
		}
		return result;*/
	}

	/**
	 * 执行有更新操作
	 * 
	 * @param sql
	 *            预编译SQL语句
	 * @param parameter
	 *            参数对象
	 * @return 变化行数
	 */
	public int executeUpdate(String sql, final SQLParameter parameter)
			throws DbException {
		int updateRows;
		//Object result = null;
		//ResultSet rs = null;
		try {
			JdbcTemplate jt=new JdbcTemplate(dataSource);
			PreparedStatementCallback<Integer> psc=new PreparedStatementCallback<Integer>() {
				
				@Override
				public Integer doInPreparedStatement(PreparedStatement ps)
						throws SQLException, DataAccessException {
					// TODO Auto-generated method stub
					if (parameter != null) {
						DBUtil.setStatementParameter(ps, parameter);
					}
					int rows=ps.executeUpdate();
					return rows;
				}
			};
		
			updateRows=jt.execute(sql,psc);
	
		}catch(Exception e){
			throw ExceptionFactory.getException(1, e.getMessage(), new SQLException(e));
		}
		return updateRows;
//		
//		try {
//			if ((!sql.equalsIgnoreCase(lastSQL)) || (prepStatement == null)) {
//				if (prepStatement != null) {
//					closeStmt(prepStatement);
//				}
//				prepStatement = conn.prepareStatement(sql);
//				lastSQL = sql;
//			}
//			prepStatement.clearParameters();
//			DBUtil.setStatementParameter(prepStatement, parameter);
//			updateRows = prepStatement.executeUpdate();
//		} catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		} catch (NullPointerException e) {
//			SQLException e1 = new SQLException("db connection has interrupted!");
//			throw ExceptionFactory.getException(dbType, e1.getMessage(), e1);
//		}
//		return updateRows;
	}

	/**
	 * 执行无更新操作
	 * 
	 * @param sql
	 *            更新SQL语句
	 * @return 更新行数
	 */
//	public int executeUpdate(String sql) throws DbException {
//		int updateRows = 0;
//
//		try {
//			if (statement == null)
//				statement = conn.createStatement();
//			updateRows = statement.executeUpdate(sql);
//		} catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		} catch (NullPointerException e) {
//			SQLException e1 = new SQLException("db connection has interrupted!");
//			throw ExceptionFactory.getException(dbType, e1.getMessage(), e1);
//		}
//		return updateRows;
//	}

//	/**
//	 * 添加有参数批量查询
//	 * 
//	 * @param sql
//	 * @param parameters
//	 */
//	public void addBatch(String sql, SQLParameter parameters)
//			throws DbException {
//		if (batch == null)
//			batch = new Batch();
//		try {
//			batch.addBatch(sql, parameters);
//			size++;
//			if (size % batchSize == 0) {
//				batchRows = internalExecuteBatch();
//				size = 0;
//			}
//		} catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		} catch (NullPointerException e) {
//			SQLException e1 = new SQLException("db connection has interrupted!");
//			throw ExceptionFactory.getException(dbType, e1.getMessage(), e1);
//		}
//	}

	/**
	 * 添加有参数批量查询
	 * 
	 * @param sql
	 * @param parameters
	 */
//	public void addBatch(String sql, SQLParameter[] parametersArray)
//			throws DbException {
//		try {
//			if (batch == null)
//				batch = new Batch();
//			size = size + parametersArray.length;
//			batch.addBatch(sql, parametersArray);
//			if (size % batchSize == 0 || size > batchSize) {
//				batchRows = internalExecuteBatch();
//				size = 0;
//			}
//		} catch (SQLException e) {
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		} catch (NullPointerException e) {
//			SQLException e1 = new SQLException("db connection has interrupted!");
//			throw ExceptionFactory.getException(dbType, e1.getMessage(), e1);
//		}
//	}

//	/**
//	 * 添加无参数批量查询
//	 * 
//	 * @param sql
//	 */
//	public void addBatch(String sql) throws DbException {
//		addBatch(sql, (SQLParameter) null);
//	}

//	private int internalExecuteBatch() throws DbException {
//		try {
//			int rows = 0;
//			if (batch != null) {
//				rows = batchRows + batch.executeBatch();
//			}
//			batchRows = 0;
//			size = 0;
//			return rows;
//		} catch (SQLException e) {
//			Logger.error(this,"execute batch exception", e.getNextException());
//			throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//		} catch (NullPointerException e) {
//			SQLException e1 = new SQLException("db connection has interrupted!");
//			throw ExceptionFactory.getException(dbType, e1.getMessage(), e1);
//		}
//	}

	public int executeBatch(String sql, final SQLParameter[] parametersArray) throws DbException {
		try {
			JdbcTemplate jt=new JdbcTemplate(dataSource);
			BatchPreparedStatementSetter bpss=new BatchPreparedStatementSetter(){

				@Override
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
						SQLParameter sp = parametersArray[i];
						if (sp != null) {
							DBUtil.setStatementParameter(ps, sp);
						}
					
				}

				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return parametersArray==null?0:parametersArray.length;
				}
				
			};
	jt.batchUpdate(sql, bpss);
	
		} finally {
		
		}
		return parametersArray==null?0:parametersArray.length;
	}

	public int executeBatch(Connection conn, String sql, final SQLParameter[] parametersArray) throws DbException {
		try {
			dzfJDBCTemplet jt=new dzfJDBCTemplet(dataSource);
			BatchPreparedStatementSetter bpss=new BatchPreparedStatementSetter(){

				@Override
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
						SQLParameter sp = parametersArray[i];
						if (sp != null) {
							DBUtil.setStatementParameter(ps, sp);
						}
					
				}

				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return parametersArray==null?0:parametersArray.length;
				}
				
			};
			int[] r= jt.batchUpdate(conn, sql, bpss);
	
		} finally {
		
		}
		return parametersArray==null?0:parametersArray.length;
	}
	public SuperVO execute(ConnectionCallback<SuperVO> action) throws Exception {
		dzfJDBCTemplet djt=new dzfJDBCTemplet(dataSource);
		return djt.execute(action);
	}
	public List<Object[][]> execute1(ConnectionCallback<List<Object[][]>> action) throws Exception {
		dzfJDBCTemplet djt=new dzfJDBCTemplet(dataSource);
		return djt.execute(action);
	}
	public int executeBatch(List<String> sql, final List<SQLParameter[]> pa) throws Exception {
		int ne=0;
		try {
			int len=sql==null?0:sql.size();
			dzfJDBCTemplet djt=new dzfJDBCTemplet(dataSource);
			List<BatchPreparedStatementSetter> lb=new ArrayList<BatchPreparedStatementSetter>();
			for (int i = 0; i <len; i++) {
				final SQLParameter[] parametersArray=pa.get(i);
				BatchPreparedStatementSetter bpss=new BatchPreparedStatementSetter(){
				
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
							SQLParameter sp = parametersArray[i];
							if (sp != null) {
								DBUtil.setStatementParameter(ps, sp);
							}
						
					}

					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return parametersArray==null?0:parametersArray.length;
					}
					
				};
			lb.add(bpss);
			}
			int[] r= djt.batchUpdate(sql, lb);
			if(r!=null&&r.length>0)
				ne=r[0];
		}catch(Exception e){
			throw e;
		}
		return ne;
//			JdbcTemplate jt=new JdbcTemplate(dataSource);
//			BatchPreparedStatementSetter bpss=new BatchPreparedStatementSetter(){
//
//				@Override
//				public void setValues(PreparedStatement ps, int i)
//						throws SQLException {
//						SQLParameter sp = parametersArray[i];
//						if (sp != null) {
//							DBUtil.setStatementParameter(ps, sp);
//						}
//					
//				}
//
//				@Override
//				public int getBatchSize() {
//					// TODO Auto-generated method stub
//					return parametersArray==null?0:parametersArray.length;
//				}
//				
//			};
//	jt.batchUpdate(sql, bpss);
//	
//		} finally {
//		
//		}
//		return parametersArray==null?0:parametersArray.length;
	}
	/**
	 * 关闭数据库连接
	 */
	public void closeAll() {
//		closeStmt(statement);
//		closeStmt(prepStatement);
//		closeConnection(conn);
	}

	/**
	 * 得到当前数据库的MetaData
	 * 
	 * @return 返回当前数据库的MetaData
	 * @throws SQLException
	 */
//	public DatabaseMetaData getMetaData() {
//		if (dbmd == null)
//			try {
//				dbmd = conn.getMetaData();
//			} catch (SQLException e) {
//				Logger.error(this,"get metadata error", e);
//			}
//		return dbmd;
//	}

	/**
	 * 创建事物处理类
	 * 
	 * @return JdbcTransaction
	 */
//	public JdbcTransaction createTransaction() {
//		return new JdbcTransaction(this);
//	}

//	private class BatchStruct {
//		String sql = null;
//
//		SQLParameter[] params;
//
//		public BatchStruct(String sql, SQLParameter[] params) {
//			this.sql = sql;
//			this.params = params;
//		}
//
//		public BatchStruct(String sql, SQLParameter param) {
//			this.sql = sql;
//			if (param != null) {
//				this.params = new SQLParameter[] { param };
//			}
//		}
//	}

	/**
	 * 私有Batch类
	 */
//	private class Batch {
//
//		private List<BatchStruct> batchStructs = new ArrayList<BatchStruct>();
//
//		private Map<String, PreparedStatement> cachedStatement = new HashMap<String, PreparedStatement>();
//
//		private Statement stmt = null;
//
//		public Batch() {
//		}
//
//		public void addBatch(String sql, SQLParameter[] pas)
//				throws SQLException {
//			batchStructs.add(new BatchStruct(sql, pas));
//		}
//
//		public void addBatch(String sql, SQLParameter pa) throws SQLException {
//			batchStructs.add(new BatchStruct(sql, pa));
//		}
//
////		private Statement getStatement(String sql, boolean prepare)
////				throws SQLException {
////			if (prepare) {
////				PreparedStatement stmt = cachedStatement.get(sql);
////				if (stmt == null) {
////					stmt = conn.prepareStatement(sql);
////					cachedStatement.put(sql, stmt);
////				}
////				return stmt;
////			} else {
////				if (stmt == null) {
////					stmt = conn.createStatement();
////				}
////				return stmt;
////			}
////		}
//
////		public int executeBatch() throws SQLException {
////			int totalRowCount = 0;
////			Iterator<BatchStruct> itr = batchStructs.iterator();
////			int rbSize = 0;
////			Statement lastStmt = null;
////			String lastSql = null;
////			while (itr.hasNext()) {
////				BatchStruct bs = itr.next();
////				itr.remove();
////				Statement now = getStatement(bs.sql, bs.params != null);
////				if (now != lastStmt) {
////					if (lastStmt != null) {
////						totalRowCount += internalExecute(lastStmt);
////						rbSize = 0;
////						if (now != stmt) {
////							closeStmt(lastStmt);
////							cachedStatement.remove(lastSql);
////						}
////					}
////					lastStmt = now;
////					lastSql = bs.sql;
////				}
////				if (bs.params != null) {
////					PreparedStatement ps = (PreparedStatement) now;
////					for (SQLParameter parameter : bs.params) {
////						if (parameter != null) {
////							DBUtil.setStatementParameter(ps, parameter);
////						}
////						ps.addBatch();
////						rbSize++;
////						if (rbSize % batchSize == 0) {
////							totalRowCount += internalExecute(ps);
////						}
////					}
////				} else {
////					now.addBatch(bs.sql);
////					rbSize++;
////					if (rbSize % batchSize == 0) {
////						totalRowCount += internalExecute(now);
////					}
////
////				}
////			}
////
////			if (lastStmt != null && rbSize % batchSize != 0) {
////				totalRowCount += internalExecute(lastStmt);
////			}
////
////			return totalRowCount;
////		}
//
////		private int internalExecute(Statement ps) throws SQLException {
////			int tc = 0;
////			int[] rowCounts = ps.executeBatch();
////			for (int j = 0; j < rowCounts.length; j++) {
////				if (rowCounts[j] == Statement.SUCCESS_NO_INFO) {
////				} else if (rowCounts[j] == Statement.EXECUTE_FAILED) {
////					// throw new SQLException("批量执行第 " + j + "条语句出错！");
////				} else {
////					tc += rowCounts[j];
////				}
////			}
////			return tc;
////
////		}
//
//		/**
//		 * 清理批量查询
//		 */
//		public void cleanupBatch() throws DbException {
//			Map<String, PreparedStatement> old = cachedStatement;
//			cachedStatement = new HashMap<String, PreparedStatement>();
//			for (PreparedStatement ps : old.values()) {
//				closeStmt(ps);
//			}
//			batchStructs.clear();
//			closeStmt(stmt);
//			stmt = null;
//		}
//	}

	/**
	 * 返回数据库连接
	 * 
	 * @return 返回 conn。
	 */
//	public Connection getConnection() {
//
//		return conn;
//	}

	/**
	 * @return 返回 dbType。
	 */
	public int getDbType() {
		return dbType;
	}

	private void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
		}
	}

	private void closeStmt(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
		}
	}

	private void closeRs(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
		}
	}
	// private boolean isSelectStatement(String sql) {
	// StringBuffer sb = new StringBuffer(sql.trim());
	// String s = (sb.substring(0, 6));
	// return (s.equalsIgnoreCase("SELECT"));
	// }

	// private boolean isSupportBatch() throws SQLException {
	// return getMetaData().supportsBatchUpdates();
	// }
}
