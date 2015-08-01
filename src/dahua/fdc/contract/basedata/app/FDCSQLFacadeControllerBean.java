package com.kingdee.eas.fdc.basedata.app;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.bos.sql.shell.trace.KSqlMonitor;
import com.kingdee.bos.sql.shell.trace.TraceInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.db.SQLUtils;

public class FDCSQLFacadeControllerBean extends AbstractFDCSQLFacadeControllerBean
{
    private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.FDCSQLFacadeControllerBean");
    
    /**
     * 执行更新
     * @Modify By Owen_wen 2010-11-09 直接调用DbUtil.execute(ctx,sql, objs)方法在DB2下会有问题，<p>
     * 因为没有考虑到传入的参数为null的情况，修改此方法。
     * @see FDCSQLFacadeControllerBean._executeBatch(Context ctx, String sql, List params)
     */
    protected void _executeUpdate(Context ctx, String sql, List objs)throws BOSException
    {   	
    	if(sql==null || objs ==null){
			return;
		}
    	
    	Object[] params = listToArray(objs);    	
        Connection conn = null;
        PreparedStatement ps = null; 
		
        try
        {
        	conn = EJBFactory.getConnection(ctx);
        }
        catch(SQLException exc)
        {
        	SQLUtils.cleanup(conn);
        	throw new BOSException("CONFIG_EXCEPTION", exc);
        }

        try
        {
            ps = conn.prepareStatement(sql);
    		ParameterMetaData pmd = null;
    		try{
    			pmd = ps.getParameterMetaData();
    		}catch(Throwable t){
    			// @AbortException
    			logger.info("do nothing, 部分数据库不支持此方法。 如oracle");
    			logger.info(t.getMessage());
    		}
    		
            for(int i = 0; i < params.length; i++)
            {
        		// DB2和MSSQL存储的0或者0.0000001是指数形式，如0E-10，需要明确调用setBigDecimal。
				// Added Adward_huang 2012-08-14
             	if (params[i] != null || pmd == null) {
					if (params[i] instanceof BigDecimal) {
						ps.setBigDecimal(i + 1, (BigDecimal) params[i]);
					} else {
						ps.setObject(i + 1, params[i]);
					}
				} else {
					ps.setNull(i + 1, pmd.getParameterType(i + 1));
				}
            }

            ps.execute();
        }
        catch(SQLException exc)
        {
        	// @AbortException
        	StringBuffer sb = new StringBuffer("");
        	for(int i =0 ;i < params.length ;i++)
        	{
        		sb.append("param ").append(i).append(" is:").append(params[i]);
        	}
        	logger.error("222 sql is:" + sql + " param is:" + sb.toString(),exc);
            throw new BOSException("Sql222 execute exception : " + sql, exc);
        }
        finally
        {
            SQLUtils.cleanup(ps, conn);
        }
    }

	private Object[] listToArray(List params) {
		Object[] objs = params == null ? new Object[0] : params.toArray();
		return objs;
	}

	/**
	 * 执行查询
	 */
	protected IRowSet _executeQuery(Context ctx, String sql, List params) throws BOSException {
		Object[] objs = listToArray(params);
		IRowSet rowSet=null;
		try{
			rowSet=DbUtil.executeQuery(ctx, sql, objs);
		}catch(BOSException e){
			String msg="execute sql error,sql:\n"+sql+"\nparam:\n"+params.toString()+"\n";
			logger.error(msg);
			throw new BOSException(e);
		}
		return rowSet;
	}

	/**
	 * 取应用服务的当前时间
	 */
	protected Timestamp _getServerTime(Context ctx) throws BOSException {
		return new Timestamp(System.currentTimeMillis());
	}
	
	protected void _setSQLTrace(Context ctx, boolean enable, String logFile)
			throws BOSException {
		if(FDCHelper.isEmpty(logFile)) logFile="console";
		Map map = KSqlMonitor.getTraceInfoMap();
		for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    String url = (String) entry.getKey();
		    TraceInfo traceInfo = (TraceInfo) entry.getValue();
		    traceInfo.enable = enable; //打开跟踪功能
		    traceInfo.logFileUrl = logFile; //输出到控制台
		}
	}
	
	/**
	 * 关闭数据库连接对象
	 * @param conn Connection对象
	 * @param st Statement对象
	 */
	protected void closeJDBC(Connection conn, Statement st) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
			if (st != null) {
				st.close();
				st = null;
			}
		} catch (SQLException e) {
			// @AbortException
			logger.error(e);
		}
	}
	
	protected void _executeBatch(Context ctx, List sqls) throws BOSException {
		if(sqls==null||sqls.size()==0){
			return;
		}
		Connection con=null;
		Statement st =null;
		try{
			con= getConnection(ctx);
			st=con.createStatement();
			for(Iterator iter=sqls.iterator();iter.hasNext();){
				String sql=(String)iter.next();
				st.addBatch(sql);
			}
			st.executeBatch();
		}catch (Exception e) {
			//将sql及数据加到异常里面 by sxhong 2009-05-20 11:07:14
			String msg="executeBatch sql error,sql:\n"+sqls.toString()+"\n";
			logger.error(msg);
			throw new BOSException(e);
		}finally{
			closeJDBC(con, st);
		}
	}
	private final int MAX_DATA_ROW = 8000; //经查:数据量太大,executeBatch有问题,只能限定每次executeBatch的最大数据
	protected void _executeBatch(Context ctx, String sql, List params) throws BOSException {
		if(sql==null||params==null||params.size()==0){
			return;
		}
		Connection con=null;
		PreparedStatement pst =null;
		try{
			con= getConnection(ctx);
			pst=con.prepareStatement(sql);
			ParameterMetaData pmd = null;
			try{
				pmd = pst.getParameterMetaData();
			}catch(Throwable t){
				// @AbortException
				logger.info("do nothing, 部分数据库不支持此方法。 如oracle");
			}
			int k = 1;
            boolean flag = false;
            int paramsIndex = 0;
			for(Iterator iter=params.iterator();iter.hasNext();){				
				Object obj=(Object)iter.next();
				if(obj==null||!(obj instanceof List)){
					throw new BOSException("无效的参数，必须为List");
				}
				List list=(List)obj;
				for(int i=0;i<list.size();i++){
					if(list.get(i) != null || pmd == null){
						pst.setObject(i+1, list.get(i));
					}else{
						pst.setNull(i+1, pmd.getParameterType(i+1));
					}
				}
				pst.addBatch();
				
				if(paramsIndex == k * MAX_DATA_ROW){
                    flag = true;
                }
                if(paramsIndex > k * MAX_DATA_ROW && flag){
                	pst.executeBatch();
                    k++;
                    flag = false;
                    pst.clearBatch();
                }
                paramsIndex ++;
			}
			pst.executeBatch();
		}catch (Exception e) {
			//将sql及数据加到异常里面 by sxhong 2009-05-20 11:07:14
			String msg="executeBatch sql error,sql:\n"+sql+"\nparam:\n"+params.toString()+"\n";
			logger.error(msg);
			throw new BOSException(e);
		}finally{
			closeJDBC(con, pst);
		}
	}
}