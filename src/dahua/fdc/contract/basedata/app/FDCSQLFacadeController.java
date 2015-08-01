package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import java.util.List;
import java.sql.Timestamp;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCSQLFacadeController extends BizController
{
    public void executeUpdate(Context ctx, String sql, List params) throws BOSException, RemoteException;
    public IRowSet executeQuery(Context ctx, String sql, List params) throws BOSException, RemoteException;
    public Timestamp getServerTime(Context ctx) throws BOSException, RemoteException;
    public void setSQLTrace(Context ctx, boolean enable, String logFile) throws BOSException, RemoteException;
    public void executeBatch(Context ctx, List sqls) throws BOSException, RemoteException;
    public void executeBatch(Context ctx, String sql, List params) throws BOSException, RemoteException;
}