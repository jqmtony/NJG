package com.kingdee.eas.xr.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.SqlTranslateException;
import com.kingdee.bos.sql.TransUtil;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.xr.XRSQLFacadeFactory;
import com.kingdee.jdbc.rowset.IRowSet;

public class XRSQLBuilder {
	public XRSQLBuilder()
	{
		batchType = null;
		batchSqls = null;
		batchParas = null;
		sqlSb = new StringBuffer();
		paramaters = new ArrayList();
	}
    public XRSQLBuilder(Context ctx)
    {
		this.ctx = null;
		batchType = null;
		batchSqls = null;
		batchParas = null;
		sqlSb = new StringBuffer();
		paramaters = new ArrayList();
		this.ctx = ctx;
	}
    public XRSQLBuilder(String sql)
    {
    	this();
    	sqlSb.append(sql);
    }
    public XRSQLBuilder(Context ctx, String sql)
    {
    	this(ctx);
    	sqlSb.append(sql);
    }
    public XRSQLBuilder appendSql(String sql)
    {
    	sqlSb.append(sql);
    	return this;
    }
    public void appendQuestMark()
    {
    	sqlSb.append("?");
    }
    public void appendParam(Object param)
    {
	   paramaters.add(handleParam(param));
	   sqlSb.append("?");
    }
    public void appendParam(Object param[])
    {
    	if(param.length == 0)
    		throw new IllegalArgumentException("param's length is 0!");
    	int len = param.length;
    	appendParam(param[0]);
    	for(int i = 1; i < len; i++)
        {
    		sqlSb.append(", ");
    		appendParam(param[i]);
        }
    }
    public void appendParam(String key, Object param[])
    {
    	if(param.length == 0)
        {
    		throw new IllegalArgumentException("param's length is 0!");
        } else
        {
        	appendSql(" " + key + " in (");
        	appendParam(param);
        	appendSql(")");
        	return;
        }
    }
    public void appendParam(String key, Object param)
    {
    	if(param == null)
    		appendSql(" " + key + " is null ");
    	appendSql(" " + key + "= ");
    	appendParam(param);
    }
    public void addParam(Object param)
    {
    	paramaters.add(handleParam(param));
    }
    public void addParams(Object params[])
    {
    	if(params == null || params.length == 0)
    		return;
    	for(int i = 0; i < params.length; i++)
    		addParam(params[i]);
    }
    public String getSql()
    {
    	if(sqlSb != null)
    		return sqlSb.toString();
    	else
    		return null;
    }
    public String getTestSql(int dbType)
        throws SqlTranslateException
    {
    	return TransUtil.Translate(getTestSql(), dbType);
    }
    public String getTestSql()
    {
    	StringBuffer rst = new StringBuffer();
    	String sql = getSql();
    	int len = sql.length();
    	int i = -1;
    	char val[] = sql.toCharArray();
        int off;
        for(off = 0; ++i < len && val[off + i] != '?';);
        rst.append(sql.substring(0, i));
        if(i < len)
        {
        	Iterator iter = paramaters.iterator();
        	for(; i < len; i++)
            {
        		char c = val[off + i];
        		if(c == '?')
                {
        			if(iter.hasNext())
                    {
        				Object p = iter.next();
        				rst.append(getParamString(p));
        				continue;
                    }
        			rst.append(sql.substring(i));
        			break;
                }
        		rst.append(c);
            }
        }
        return rst.toString();
    }
    private String getParamString(Object p)
    {
    	if(p == null)
    		return null;
    	if(p instanceof Boolean)
        {
    		Boolean p_new = (Boolean)p;
    		if(p_new.equals(Boolean.TRUE))
    			return "1";
    		else
    			return "0";
        }
	    if(p instanceof String)
	    	return "'" + p + "'";
	    if(p instanceof Timestamp)
	    	return "{" + FMConstants.FORMAT_TIME.format(p) + "}";
	    if(p instanceof Date)
	    	return "{" + FMConstants.FORMAT_DAY.format(p) + "}";
	    else
	    	return p.toString();
    }
    public PreparedStatement createState(Connection conn)
        throws SQLException
    {
    	return FMHelper.createState(conn, getSql(), paramaters.toArray());
    }
    public IRowSet executeQuery()
        throws BOSException
    {
    	if(ctx == null)
    		return XRSQLFacadeFactory.getRemoteInstance().executeQuery(getSql(), paramaters);
    	else
    		return executeQuery(ctx);
    }
    public void executeUpdate()
        throws BOSException
    {
    	if(ctx == null)
    		XRSQLFacadeFactory.getRemoteInstance().executeUpdate(getSql(), paramaters);
    	else
    		executeUpdate(ctx);
    }
    public void execute()
        throws BOSException
    {
    	executeUpdate();
    }
    public IRowSet executeQuery(Context ctx)
        throws BOSException
    {
    	return XRSQLFacadeFactory.getLocalInstance(ctx).executeQuery(getSql(), paramaters);
    }
    public void executeUpdate(Context ctx)
        throws BOSException
    {
    	XRSQLFacadeFactory.getLocalInstance(ctx).executeUpdate(getSql(), paramaters);
    }
    public boolean isExist()
        throws BOSException
    {
    	IRowSet rowSet = executeQuery();
    	return rowSet != null && rowSet.size() > 0;
    }
    public List getParamaters()
    {
    	return paramaters;
    }
    public SqlParams getSqlParams()
    {
    	if(paramaters == null || paramaters.size() == 0)
        {
    		return null;
        } else
        {
        	SqlParams sqlParams = new SqlParams();
        	sqlParams.setParams(paramaters.toArray());
        	return sqlParams;
        }
    }
    public static void main(String args[])
    {
    	String a[] = {
    			"a", "b", "c", "d"
        };
    	XRSQLBuilder builder = new XRSQLBuilder();
    	builder.appendSql("select * from t where fid in(");
    	builder.appendParam(a);
    	builder.appendSql(")");
    	System.out.print(builder.getSql());
    	System.out.println(builder.getTestSql());
    }
    public void appendFilter(String colName, Set ids, CompareType include)
    {
    	if(ids == null || ids.size() == 0)
        {
    		return;
        } else
        {
        	appendSql(" and ");
        	appendParam(colName, ids.toArray());
        	return;
        }
    }
    public void clear()
    {
    	sqlSb.setLength(0);
    	paramaters.clear();
    	batchType = null;
    	batchSqls = null;
    	batchParas = null;
    }
    private Object handleParam(Object param)
    {
    	if(param instanceof Date)
    		return DateXRHelper.getSqlDate((Date)param);
    	else
    		return param;
    }
    public static void setSQLTrace(boolean enable)
        throws BOSException
    {
    	setSQLTrace(enable, null);
    }
    public static void setSQLTrace(boolean enable, String logFile)
        throws BOSException
    {
    	if(StringXRHelper.isEmpty(logFile))
    		logFile = "console";
    	XRSQLFacadeFactory.getRemoteInstance().setSQLTrace(enable, logFile);
    }
    public void setBatchType(String type)
    {
    	batchType = type;
    	if(batchType != null)
        {
    		if(batchType.equals("StatementType"))
            {
    			batchSqls = new ArrayList();
    			batchParas = null;
            }
    		if(batchType.equals("PrepareStatement"))
    			batchParas = new ArrayList();
        }
    }
    public void addBatch(String sql)
    {
    	batchSqls.add(sql);
    }
    public void setPrepareStatementSql(String sql)
    {
    	clear();
    	sqlSb.append(sql);
    }
    public void addBatch()
    {
    	batchParas.add(paramaters);
    	paramaters = new ArrayList();
    }
    public void executeBatch()
        throws BOSException
    {
    	if(batchType == null)
    		return;
    	if(batchType.equals("StatementType"))
    		executeBatch(batchSqls);
    	if(batchType.equals("PrepareStatement"))
    		executeBatch(sqlSb.toString(), batchParas);
    }
    public void executeBatch(List sqls)
        throws BOSException
    {
    	if(ctx == null)
    		XRSQLFacadeFactory.getRemoteInstance().executeBatch(sqls);
    	else
    		XRSQLFacadeFactory.getLocalInstance(ctx).executeBatch(sqls);
    }
    public void executeBatch(String sql, List params)
        throws BOSException
    {
    	if(ctx == null)
    		XRSQLFacadeFactory.getRemoteInstance().executeBatch(sql, params);
    	else
    		XRSQLFacadeFactory.getLocalInstance(ctx).executeBatch(sql, params);
    }
    public static String getInSql(String key, String params[])
    {
    	StringBuffer sql = new StringBuffer(key + " in (");
    	for(int i = 0; i < params.length; i++)
    		if(i > 0)
    			sql.append(",'" + params[i] + "'");
    		else
    			sql.append("'" + params[i] + "'");
    	sql.append(')');
    	return sql.toString();
    }
    public static final String COMMA = ", ";
    private static final String RIGHT_BRACKET = "}";
    private static final String LEFT_BRACKET = "{";
    private static final String SINGLE_QUOTES = "'";
    public static final String QUEST_MARK = "?";
    private StringBuffer sqlSb;
    private List paramaters;
    private Context ctx;
    static final char oldChar = 63;
    public static final String STATEMENT_TYPE = "StatementType";
    public static final String PREPARESTATEMENT_TYPE = "PrepareStatement";
    private String batchType;
    private List batchSqls;
    private List batchParas;
}
