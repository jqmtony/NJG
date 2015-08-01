package com.kingdee.eas.fdc.basedata;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.query.server.QueryConnectionProvider;
import com.kingdee.bos.db.TempTablePool;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.DbType;
import com.kingdee.bos.sql.KSqlUtil;
import com.kingdee.bos.sql.SqlTranslateException;
import com.kingdee.bos.sql.TransUtil;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 房地产SQL工具类,构造SQL,执行SQL
 * @author liupd
 *
 */
public class FDCSQLBuilder {
	private static Logger log = Logger.getLogger("com.kingdee.eas.fdc.basedata.FDCSQLBuilder");
	private static final int MAX_PARAM_LENGTH = 1500;
	public static final String COMMA = ", ";

	private static final String RIGHT_BRACKET = "}";

	private static final String LEFT_BRACKET = "{";

	private static final String SINGLE_QUOTES = "'";

	final public static String QUEST_MARK = "?";

	private StringBuffer sqlSb;

	private List paramaters;
	private Context ctx=null;
	private TempTablePool pool;
	private List tempTblNames;
	public FDCSQLBuilder() {
		super();
		sqlSb = new StringBuffer();
		paramaters = new ArrayList();
	}
	
	public FDCSQLBuilder(Context ctx) {
		super();		
		sqlSb = new StringBuffer();
		paramaters = new ArrayList();
		this.ctx=ctx;
	}
	
	/**
	 * sql
	 * 
	 * @param sql
	 */
	public FDCSQLBuilder(String sql) {
		this();
		sqlSb.append(sql);
	}
	public FDCSQLBuilder(Context ctx,String sql) {
		this(ctx);
		sqlSb.append(sql);
	}
	/**
	 * 附加sql
	 * 
	 * @param sql
	 * @return
	 */
	public FDCSQLBuilder appendSql(String sql) {
		sqlSb.append(sql);
		return this;
	}

	/**
	 * 附加问号(?)
	 * 
	 */
	public void appendQuestMark() {
		sqlSb.append(QUEST_MARK);
	}

	/**
	 * 附加参数
	 * 
	 * @param param
	 */
	public void appendParam(Object param) {
		paramaters.add(handleParam(param));
		sqlSb.append(QUEST_MARK);

	}

	/**
	 * 
	 * 描述：附加参数(数组)
	 * 
	 * @param param
	 * @author:liupd 创建时间：2005-11-9
	 *               <p>
	 */
	public void appendParam(Object[] param) {
		if (param.length == 0) {
			throw new IllegalArgumentException("param's length is 0!");
		}
		int len = param.length;
		appendParam(param[0]);
		for (int i = 1; i < len; i++) {
			this.sqlSb.append(COMMA);
			appendParam(param[i]);
		}
	}
	/**
	 * 当param数据较多时，如超过2000个SQL Server会报错， 因此需要使用临时表来解决
	 * @param column 列名
	 * @param param 参数
	 * @param columnType 列在数据库表中的类型
	 * @throws BOSException
	 * 注意此方法只能在服务端调用， 若在客户端调用将不会使用临时表。 调用此方法后必须调用releasTempTables方法以释放临时表资源
	 */
	public void appendParam(String column, Object[] param, String columnType) throws BOSException {
		this.appendParam(column, param, columnType, false);
	}

	/**
	 * 描述：是否强制使用临时表解决in查询的问题
	 * @see FDCSQLBuilder#appendParam(String column, Object[] param, String columnType)
	 * @param columnType 该参数为创建临时表时，该值的类型。例如 "varchar(44)" 
	 * @param isForceUseTempTable 参数为true时，强制使用临时表方式，调用此方法后必须调用releasTempTables方法以释放临时表资源
	 * @Author：owen_wen
	 * @CreateTime：2012-2-6
	 */
	public void appendParam(String column, Object[] param, String columnType, boolean isForceUseTempTable) throws BOSException {
		try {
			if (ctx != null && (isForceUseTempTable || (param != null && param.length > MAX_PARAM_LENGTH && isSQLServer()))) {
				pool = TempTablePool.getInstance(ctx);
				String tempTblName = initTempTable(pool, param, columnType);
				appendSql(" " + column + " in (");
				appendSql("select FTMPColumn from " + tempTblName);
				appendSql(")");
			} else {
				appendParam(column, param);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BOSException(e);
		}
	}
	
	private String initTempTable(TempTablePool pool, Object[] param, String columnType) throws Exception {
		String createSQL = "CREATE TABLE T_TMP_RESQLBUILDER1(FTMPColumn " + columnType + ")";
		log.info(createSQL);
		String tempTblName = pool.createTempTable(createSQL);
		if (tempTblNames == null) {
			tempTblNames = new ArrayList();
		}
		tempTblNames.add(tempTblName);
		String insertSQL = "INSERT INTO " + tempTblName + " (FTMPColumn) values (?)";
		log.info(insertSQL);
		List sqlParam = new ArrayList();
		for (int i = 0; i < param.length; ++i) {
			sqlParam.add(Arrays.asList(new Object[] { param[i] }));
		}
		executeBatch(insertSQL, sqlParam);
		return tempTblName;
	}

	/**
	 * 
	 * @param key
	 * @param param
	 * @deprecated
	 * @see com.kingdee.eas.fdc.basedata.FDCSQLBuilder.appendParam(String column, Object[] param, String columnType) 
	 * 由于在SQL Server下，有2000个参数的限制。 因此需要使用临时表来解决此问题。 
	 */
	public void appendParam(String key, Object[] param) {
		if (param.length == 0) {
			throw new IllegalArgumentException("param's length is 0!");
		}
		appendSql(" " + key + " in (");
		appendParam(param);
		appendSql(")");
	}
	
	public void appendParam(String key, Object param) {
		if(param==null){
			appendSql(" "+key+" is null ");
		}else{
			
		}
		appendSql(" " + key+"= ");
		appendParam(param);
	}

	/**
	 * 添加参数
	 * 
	 * @param param
	 */
	public void addParam(Object param) {
		paramaters.add(handleParam(param));
	}

	public void addParams(Object[] params) {
		if (params == null || params.length == 0) {
			return;
		}
		for (int i = 0; i < params.length; i++) {
			addParam(params[i]);
		}

	}

	/**
	 * 获取最终sql
	 * 
	 * @return
	 */
	public String getSql() {
		if (sqlSb != null) {
			return sqlSb.toString();
		}
		return null;

	}

	/**
	 * 获取指定数据库的sql
	 * 
	 * @see com.kingdee.bos.sql.DbType;
	 * @param dbType
	 * @return
	 * @throws SqlTranslateException
	 */
	public String getTestSql(int dbType) throws SqlTranslateException {
		return TransUtil.Translate(getTestSql(), dbType);

	}

	final static char oldChar = '?';

	/**
	 * 获取测试script ,将参数?替换为真实参数
	 * 
	 * @return
	 */
	public String getTestSql() {
		StringBuffer rst = new StringBuffer();
		String sql = getSql();
		int len = sql.length();
		int i = -1;
		char[] val = sql.toCharArray(); /* avoid getfield opcode */
		int off = 0; /* avoid getfield opcode */

		while (++i < len) {
			if (val[off + i] == oldChar) {
				break;
			}
		}
		rst.append(sql.substring(0, i));
		if (i < len) {
			Iterator iter = paramaters.iterator();
			while (i < len) {
				char c = val[off + i];
				if (c == oldChar) {
					if (iter.hasNext()) {
						Object p = iter.next();
						rst.append(getParamString(p));
					} else {
						rst.append(sql.substring(i));
						break;
					}
				} else {
					rst.append(c);
				}
				i++;
			}
		}
		return rst.toString();
	}

	/**
	 * 将参数转换为对应的字符串， 处理日期，字符串，boolean
	 * 
	 * @param p
	 * @return
	 */
	public  String getParamString(Object p) {
		if (p == null) {
			return null;
		}
		if (p instanceof Boolean) {
			Boolean p_new = (Boolean) p;
			if (p_new.equals(Boolean.TRUE)) {
				return "1";
			} else {
				return "0";
			}
		}
		if (p instanceof String) {
			return SINGLE_QUOTES + p + SINGLE_QUOTES;
		}
		if (p instanceof Timestamp) {
			return LEFT_BRACKET + SINGLE_QUOTES + FDCConstants.FORMAT_TIME.format(p) + SINGLE_QUOTES
					+ RIGHT_BRACKET;
		}
		if (p instanceof Date) {
			return LEFT_BRACKET + SINGLE_QUOTES + FDCConstants.FORMAT_DAY.format(p) + SINGLE_QUOTES
					+ RIGHT_BRACKET;
		}
		return p.toString();
	}

	/**
	 * 建立 statement
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement createState(java.sql.Connection conn)
			throws SQLException {
		return FMHelper.createState(conn, getSql(), paramaters.toArray());
	}

	public IRowSet executeQuery() throws BOSException {
		if(ctx==null){
			return FDCSQLFacadeFactory.getRemoteInstance().executeQuery(getSql(), paramaters);
		}else{
			return executeQuery(ctx);
		}
		
	}

	public void executeUpdate() throws BOSException {
		if(ctx==null){
			FDCSQLFacadeFactory.getRemoteInstance().executeUpdate(getSql(), paramaters);
		}else{
			executeUpdate(ctx);
		}
	}

	public void execute() throws BOSException {
		executeUpdate();
	}
	public IRowSet executeQuery(Context ctx) throws BOSException {
		return FDCSQLFacadeFactory.getLocalInstance(ctx).executeQuery(getSql(), paramaters);
	}

	public void executeUpdate(Context ctx) throws BOSException {
		FDCSQLFacadeFactory.getLocalInstance(ctx).executeUpdate(getSql(), paramaters);
	}
	public boolean isExist()  throws BOSException{
		IRowSet rowSet=executeQuery();
		if(rowSet!=null&&rowSet.size()>0){
			return true;
		}
		return false;
	}
	/**
	 * 获取参数
	 * 
	 * @return
	 */
	public List getParamaters() {
		return paramaters;
	}

	public SqlParams getSqlParams() {
		if (paramaters == null || paramaters.size() == 0) {
			return null;
		}
		SqlParams sqlParams = new SqlParams();
		sqlParams.setParams(paramaters.toArray());
		return sqlParams;
	}

	public void appendFilter(String colName, Set ids, CompareType include) {
		if (ids == null || ids.size() == 0) {
			return;
		}
		appendSql(" and ");
		appendParam(colName, ids.toArray());
	}
	
	public void clear(){
		sqlSb.setLength(0);
		paramaters.clear();
		batchType=null;
		batchSqls=null;
		batchParas=null;
	}
	
	private Object handleParam(Object param) {
		if(param instanceof java.util.Date){
			return FDCDateHelper.getSqlDate((java.util.Date)param);
		}
		if(param instanceof java.sql.Timestamp){
			return FDCDateHelper.getSqlDate((java.sql.Timestamp)param);
		}
		return param;
	}
	
	/**
	 * 关闭打开KSQL 的Trace功能，输出到控制台,仅在客户端调用
	 * by sxhong 
	 * @param enable
	 */
	public static void setSQLTrace(boolean enable) throws BOSException{
		setSQLTrace(enable, null);

	}
	
	/**
	 * 关闭打开KSQL 的Trace功能，输出到文件，仅在客户端调用
	 * by sxhong
	 * @param enable
	 * @param logFileUrl
	 */
	public static void setSQLTrace(boolean enable,String logFile) throws BOSException{
		if(FDCHelper.isEmpty(logFile)) logFile="console";
		FDCSQLFacadeFactory.getRemoteInstance().setSQLTrace(enable, logFile);
	}

/****************************************************************************/
/*
 * 如下几个方法用于批处理
 * 包括两类：
 * 一、不带参
 * FDCSQLBuilder builder=new FDCSQLBuilder();
 * setBatchType(STATEMENT_TYPE);
 * builder.addBatch(sql);
 * builder.addBatch(sql);
 * builder.addBatch(sql);
 * builder.executeBatch();
 * 二、带参  
 * FDCSQLBuilder builder=new FDCSQLBuilder();
 * setBatchType(PREPARESTATEMENT_TYPE);
 * builder.setPrepareStatementSql(insert into table values(?,?,?));
 * builder.addParam(p1);
 * builder.addParam(p2);
 * builder.addParam(p3);
 * builder.addBatch();
 * 
 * builder.addParam(p1);
 * builder.addParam(p2);
 * builder.addParam(p3);
 * builder.addBatch();
 * builder.executeBatch();
 * 
 * 也可以通过带参的executeBatch进行调用；
 */
	public static final String STATEMENT_TYPE="StatementType";
	public static final String PREPARESTATEMENT_TYPE="PrepareStatement";
	private String batchType=null;
	private List batchSqls=null;
	private List batchParas=null;
	
	public void setBatchType(String type){
		batchType=type;
		if(batchType!=null){
			if(batchType.equals(STATEMENT_TYPE)){
				batchSqls=new ArrayList();
				batchParas=null;
			}
			if(batchType.equals(PREPARESTATEMENT_TYPE)){
				batchParas=new ArrayList();
			}
		}
	}
	public void addBatch(String sql){
		batchSqls.add(sql);
	}
	
	public void setPrepareStatementSql(String sql){
		clear();
		sqlSb.append(sql);
	}
	public void addBatch(){
		batchParas.add(paramaters);
		paramaters=new ArrayList();
	}

	
	public void executeBatch() throws BOSException{
		if(batchType==null){
			return;
		}
		if(batchType.equals(STATEMENT_TYPE)){
			executeBatch(batchSqls);
		}
		if(batchType.equals(PREPARESTATEMENT_TYPE)){
			executeBatch(sqlSb.toString(),batchParas);
		}
	}
	/**
	 * @param params	参数列表,params为顶层List,里面放置要执行的sql语句 
	 * <p>例:<br/>
	 * <blockquote><pre>
	 * FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
	 * 
	 * List sqls=new ArrayList();
	 * 
	 * for(int i=0;i<100;i++){
	 * 	sqls.add("update T_CON_ContractBill set famount=500 where fid=1");
	 * }
	 * builder.executeBatch(sqls);
	 * </pre></blockquote>
	 * by sxhong
	 * @throws BOSException
	 */
	public void executeBatch(List sqls) throws BOSException{
		if(ctx==null){
			FDCSQLFacadeFactory.getRemoteInstance().executeBatch(sqls);
		}else{
			FDCSQLFacadeFactory.getLocalInstance(ctx).executeBatch(sqls);
		}
	}
	
	/**
	 * @param sql  预编译的sql 
	 * @param params	参数列表,params为顶层List,里面为子List放置每个参数的列表
	 * <p>例:<br/>
	 * <blockquote><pre>
	 * FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
	 * String sql="update T_CON_ContractBill set famount=? where fid=?"
	 * List params=new ArrayList();
	 * for(int i=0;i<100;i++){
	 * 	params.add(Arrays.asList(new Object[]{famount,fid}));
	 * }
	 * builder.executeBatch(sql,params);
	 * </pre></blockquote>
	 * by sxhong
	 * @throws BOSException
	 */
	public void executeBatch(String sql,List params) throws BOSException{
		if(ctx==null){
			FDCSQLFacadeFactory.getRemoteInstance().executeBatch(sql, params);
		}else{
			FDCSQLFacadeFactory.getLocalInstance(ctx).executeBatch(sql, params);
		}
	}
	
	public static String getInSql(String key,String []params){
		StringBuffer sql=new StringBuffer(key+" in (");
		for(int i=0;i<params.length;i++){
			if(i>0){
				sql.append(",'"+params[i]+"'");
			}else{
				sql.append("'"+params[i]+"'");
			}
		}
		sql.append(')');
		return sql.toString();
	}
	
	/**
	 * 用于执行前做sql的部分替换
	 * @param regex
	 * @param newStr
	 */
	public void replaceSql(String regex,String newStr){
		String sql=sqlSb.toString();
		if(sql==null){
			return;
		}
		sqlSb.setLength(0);
		sqlSb.append(sql.replaceAll(regex, newStr));
	}
	
	/**
	 * 释放临时表
	 */
	public void releasTempTables() {
		if (pool != null && tempTblNames != null) {
			for (int i = 0; i < tempTblNames.size(); ++i) {
				pool.releaseTable((String) tempTblNames.get(i));
			}
			pool = null;
		}
	}
	
	/**
	 * 账套是否是SQL Server数据库
	 */
	private boolean isSQLServer() {
		try {
			if (ctx != null) {
				return DbType.MS_SQL_Server == KSqlUtil.getDbType(QueryConnectionProvider.getConnection(ctx));
			}
		} catch (SQLException e) {
			// @AbortException
			log.error(e.getCause(), e);
			return false;
		}
		return false;
	}
}
