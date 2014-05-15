package com.kingdee.eas.xr.app;

import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import java.lang.String;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.List;
import java.sql.Timestamp;



public abstract class AbstractXRSQLFacadeControllerBean extends AbstractBizControllerBean implements XRSQLFacadeController
{
    protected AbstractXRSQLFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("F29FFEF2");
    }

    public void executeUpdate(Context ctx, String sql, List params) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3372bc8d-059a-4657-90a0-7eb169dcae71"), new Object[]{ctx, sql, params});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _executeUpdate(ctx, sql, params);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _executeUpdate(Context ctx, String sql, List params) throws BOSException
    {    	
        return;
    }

    public IRowSet executeQuery(Context ctx, String sql, List params) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("39e9d935-1ff7-4966-95b5-84d3651c6d55"), new Object[]{ctx, sql, params});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            IRowSet retValue = (IRowSet)_executeQuery(ctx, sql, params);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (IRowSet)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IRowSet _executeQuery(Context ctx, String sql, List params) throws BOSException
    {    	
        return null;
    }

    public Timestamp getServerTime(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2254aa63-b305-4017-b873-a47d7723f661"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Timestamp retValue = (Timestamp)_getServerTime(ctx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Timestamp)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Timestamp _getServerTime(Context ctx) throws BOSException
    {    	
        return null;
    }

    public void setSQLTrace(Context ctx, boolean enable, String logFile) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c2d4faf4-15cb-48a3-8f4d-8ce8a010ec59"), new Object[]{ctx, new Boolean(enable), logFile});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _setSQLTrace(ctx, enable, logFile);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _setSQLTrace(Context ctx, boolean enable, String logFile) throws BOSException
    {    	
        return;
    }

    public void executeBatch(Context ctx, List sqls) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fe05b990-e9ad-491b-996a-5fd7a372d39f"), new Object[]{ctx, sqls});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _executeBatch(ctx, sqls);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _executeBatch(Context ctx, List sqls) throws BOSException
    {    	
        return;
    }

    public void executeBatch(Context ctx, String sql, List params) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f523e46f-3751-4180-b963-39da4b62750b"), new Object[]{ctx, sql, params});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _executeBatch(ctx, sql, params);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _executeBatch(Context ctx, String sql, List params) throws BOSException
    {    	
        return;
    }

}