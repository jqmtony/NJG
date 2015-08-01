package com.kingdee.eas.fdc.basedata.app;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.EASLogTimeInfo;
import com.kingdee.util.db.SQLUtils;

public class EASLogTimeControllerBean extends AbstractEASLogTimeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.migrate.app.EASLogTimeControllerBean");
    
    private static final String systemName = "EAS"; 
    
	private static final String JDBC = "jdbc";
	
    public static final String SERVER_CONFIG_PROP = "eas.startLog";

    public static final String DEFAULT_SERVER_CONFIG = "easStartLog.properties";
    
    //当EAS成功启动后，自动调用该事务
    protected boolean _startSucess(Context ctx, IObjectPK pk)throws BOSException, EASBizException
    {
    	//读取配置文件
        String fileName = System.getProperty(SERVER_CONFIG_PROP,
                DEFAULT_SERVER_CONFIG);
        
    	String ip = "192.168.19.244";
    	String port = "1433";
    	String dbName = "eas540";
    	String userName = "sa";
    	String password = "sa";
    	String tableName = "EasRunLog";
    	
    	if(fileName==null){
        	ip = "localhost";
        	port = "1433" ;
        	dbName = "easalert";
        	userName = "easalert_sa";
        	password = "Eas2008";
        	tableName = "EasRunLog";
    	}else{
            Properties props = new Properties();            
            FileInputStream fi;
			try {
				fi = new FileInputStream(fileName);
				props.load(new BufferedInputStream(fi));
			} catch (Exception e) {
				// @AbortException
				e.printStackTrace();
				this.addnew(ctx,insertToEAS(ctx,false,e.getMessage()));
				return false;
			}             
            
        	ip = props.getProperty("ip");
        	port = props.getProperty("port");
        	dbName = props.getProperty("dbName");
        	userName = props.getProperty("userName");
        	password = props.getProperty("password");
        	tableName = props.getProperty("tableName");
    	} 
    	
    	//获取一个数据源连接信息
        Connection conn = getConnection( ctx, ip , port, dbName, userName, password, tableName) ;
    	
        //写入sql 2005数据
        if(conn!=null){
        	insertToSql(ctx,conn, tableName);
        }
        
        return true;
    }
    
    
    //获取一个数据源连接信息
    private Connection getConnection(Context ctx,String ip ,String port,String dbName,String userName,String password,String tableName ) throws BOSException, EASBizException{
    
    	//数据源连接
    	Connection conn= null;
    	PreparedStatement stmt = null;
    	try {  	

	    	try {
				Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
		    	String url="jdbc:microsoft:sqlserver://"+ip+":"+port+";DatabaseName="+dbName; 
		    	conn= DriverManager.getConnection(url,userName,password);
	    	}
	    	catch (Exception e) {
				// @AbortException
				e.printStackTrace();
			} 
	    	
	    	if(conn==null){
	    		Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
		    	StringBuffer sb = new StringBuffer();
	            sb.append("net.sourceforge.jtds.jdbc.Driver:dbtype=mssqlserver:jdbc:jtds:sqlserver://");
	            sb.append(ip);
	            sb.append(":");
	            sb.append(port);
	            sb.append(";DatabaseName=");
	            sb.append(dbName);
	            sb.append(";SelectMethod=cursor;Charset=GBK");
	
		    	conn= DriverManager.getConnection(sb.toString(),userName,password);
	    	}
	    	
	    	//检查表是否存在
	    	String sql = "IF NOT EXISTS (SELECT * FROM SYSOBJECTS SYSOBJ WHERE SYSOBJ.NAME = '"+tableName+"')"  +
	    			" BEGIN  create table "+tableName+" (logtime Datetime,system varchar(40))  END";
	    	
            stmt = conn.prepareStatement(sql);;
            stmt.execute();     
		} catch (Exception e) {
			// @AbortException
			e.printStackTrace();
			//启动失败
			this.addnew(ctx,insertToEAS(ctx,false,e.getMessage()));
		} finally{
			SQLUtils.cleanup(stmt);
		}
    	
    	return conn;
    }
    
    //写入数据
    private EASLogTimeInfo insertToEAS(Context ctx,boolean isSuc,String message) throws EASBizException, BOSException{
    	
    	Timestamp time = new Timestamp(new Date().getTime());
    	//写入EAS数据
    	EASLogTimeInfo info = new EASLogTimeInfo();    	
    	info.setLogTime(time);
    	info.setSystem(systemName);    	
    	info.setIsSuc(isSuc);
    	
    	if(message!=null && message.length()>1000){
    		message = message.substring(0,1000);
    	}
    	info.setDescription(message);
    	
    	//IObjectPK pk = this.addnew(ctx,info);
    	
    	return info;    	
    }
    
    //写入sql 2005数据
    private void insertToSql(Context ctx,Connection conn,String tableName ) throws EASBizException, BOSException{
    	  	    	 		
    	EASLogTimeInfo info = insertToEAS(ctx,true,null);
    		
	    //写入sql 2005数据
	    String sql = " insert into "+tableName+" values(?,?) "; 
    	
	    PreparedStatement stmt = null;
	    try {
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1,info.getLogTime());
			stmt.setString(2,systemName);
				
			stmt.execute();
				
		} catch (SQLException e) {
			// @AbortException
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			
			String message = e.getMessage();
		   	if(message!=null && message.length()>1000){
	    		message = message.substring(0,1000);
	    	}
			info.setDescription(message);
			info.setIsSuc(false);
			throw new BOSException(e);
		}finally{
			SQLUtils.cleanup(conn);
			SQLUtils.cleanup(stmt);
		}    
		
		this.addnew(ctx,info);
    }

    //启动成功时间记录
	protected boolean _logTime(Context ctx, String ip, String port, String dbName, String userName, String password, String tableName, String field) throws BOSException, EASBizException {
    	
		if(ip==null || dbName==null || userName==null){
	    	//读取配置文件
	        String fileName = System.getProperty(SERVER_CONFIG_PROP,
	                DEFAULT_SERVER_CONFIG);
	    	
	    	if(fileName==null){
	        	ip = "localhost";
	        	port = "1433" ;
	        	dbName = "easalert";
	        	userName = "easalert_sa";
	        	password = "Eas2008";
	        	tableName = "EasRunLog";
	    	}else{
	            Properties props = new Properties();            
	            FileInputStream fi;
				try {
					fi = new FileInputStream(fileName);
					props.load(new BufferedInputStream(fi));
				} catch (Exception e) {
					// @AbortException
					e.printStackTrace();
					this.addnew(ctx,insertToEAS(ctx,false,e.getMessage()));
					return false;
				}             
	            
	        	ip = props.getProperty("ip");
	        	port = props.getProperty("port");
	        	dbName = props.getProperty("dbName");
	        	userName = props.getProperty("userName");
	        	password = props.getProperty("password");
	        	tableName = props.getProperty("tableName");
	        	
	        	if(port==null){
	        		port = "1433" ;
	        	}
	        	if(password==null){
	        		password = "" ;
	        	}	        	
	        	if(tableName == null){
	        		tableName = "EasRunLog";
	        	}
	    	} 
		}
		
		//获取一个数据源连接信息
        Connection conn = getConnection( ctx, ip , port, dbName, userName, password, tableName) ;
    	
        //写入sql 2005数据
        if(conn!=null){
        	insertToSql(ctx,conn,tableName);
        }
        
        return true;
	}

}