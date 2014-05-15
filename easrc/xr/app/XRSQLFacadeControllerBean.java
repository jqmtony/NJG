package com.kingdee.eas.xr.app;

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
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.xr.helper.StringXRHelper;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.db.SQLUtils;

public class XRSQLFacadeControllerBean extends AbstractXRSQLFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.xr.app.XRSQLFacadeControllerBean");
    
    /**
     * ִ�и���
     * @Modify By Owen_wen 2010-11-09 ֱ�ӵ���DbUtil.execute(ctx,sql, objs)������DB2�»������⣬<p>
     * ��Ϊû�п��ǵ�����Ĳ���Ϊnull��������޸Ĵ˷�����
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
    			logger.info("do nothing, �������ݿⲻ֧�ִ˷����� ��oracle");
    			logger.info(t.getMessage());
    		}
    		
            for(int i = 0; i < params.length; i++)
            {
        		if(params[i] != null || pmd == null){
					ps.setObject(i+1, params[i]);
				}else{
					ps.setNull(i+1, pmd.getParameterType(i+1));
				}
            }

            ps.execute();
        }
        catch(SQLException exc)
        {
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
	 * ִ�в�ѯ
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
	 * ȡӦ�÷���ĵ�ǰʱ��
	 */
	protected Timestamp _getServerTime(Context ctx) throws BOSException {
		return new Timestamp(System.currentTimeMillis());
	}
	
	protected void _setSQLTrace(Context ctx, boolean enable, String logFile)
			throws BOSException {
		if(StringXRHelper.isEmpty(logFile)) logFile="console";
		Map map = KSqlMonitor.getTraceInfoMap();
		for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    String url = (String) entry.getKey();
		    System.out.println(url);
		    TraceInfo traceInfo = (TraceInfo) entry.getValue();
		    traceInfo.enable = enable; //�򿪸��ٹ���
		    traceInfo.logFileUrl = logFile; //���������̨
		}
	}
	
	/**
	 * �ر����ݿ����Ӷ���
	 * @param conn Connection����
	 * @param st Statement����
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
			//��sql�����ݼӵ��쳣���� by sxhong 2009-05-20 11:07:14
			String msg="executeBatch sql error,sql:\n"+sqls.toString()+"\n";
			logger.error(msg);
			throw new BOSException(e);
		}finally{
			closeJDBC(con, st);
		}
	}
	private final int MAX_DATA_ROW = 8000; //����:������̫��,executeBatch������,ֻ���޶�ÿ��executeBatch���������
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
				//do nothing, �������ݿⲻ֧�ִ˷����� ��oracle
			}
			int k = 1;
            boolean flag = false;
            int paramsIndex = 0;
			for(Iterator iter=params.iterator();iter.hasNext();){				
				Object obj=(Object)iter.next();
				if(obj==null||!(obj instanceof List)){
					throw new BOSException("��Ч�Ĳ���������ΪList");
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
			//��sql�����ݼӵ��쳣���� by sxhong 2009-05-20 11:07:14
			String msg="executeBatch sql error,sql:\n"+sql+"\nparam:\n"+params.toString()+"\n";
			logger.error(msg);
			throw new BOSException(e);
		}finally{
			closeJDBC(con, pst);
		}
	}
}