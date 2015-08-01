package com.kingdee.eas.fdc.basedata.app;

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



public abstract class AbstractFDCSQLFacadeControllerBean extends AbstractBizControllerBean implements FDCSQLFacadeController
{
    protected AbstractFDCSQLFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("B5889A15");
    }

    public void executeUpdate(Context ctx, String sql, List params) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1cd2cd04-010f-1000-e000-0111c0a80e96"), new Object[]{ctx, sql, params});
            invokeServiceBefore(svcCtx);
            _executeUpdate(ctx, sql, params);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _executeUpdate(Context ctx, String sql, List params) throws BOSException;

    public IRowSet executeQuery(Context ctx, String sql, List params) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1cd2cd04-010f-1000-e000-0112c0a80e96"), new Object[]{ctx, sql, params});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_executeQuery(ctx, sql, params);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IRowSet _executeQuery(Context ctx, String sql, List params) throws BOSException;

    public Timestamp getServerTime(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b0b060f1-0115-1000-e000-014cc0a81297"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            Timestamp retValue = (Timestamp)_getServerTime(ctx);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Timestamp _getServerTime(Context ctx) throws BOSException;

    public void setSQLTrace(Context ctx, boolean enable, String logFile) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f8ad8a5f-0115-1000-e000-0069c0a81296"), new Object[]{ctx, new Boolean(enable), logFile});
            invokeServiceBefore(svcCtx);
            _setSQLTrace(ctx, enable, logFile);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setSQLTrace(Context ctx, boolean enable, String logFile) throws BOSException;

    public void executeBatch(Context ctx, List sqls) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("33fbda2f-0117-1000-e000-0002c0a81296"), new Object[]{ctx, sqls});
            invokeServiceBefore(svcCtx);
            _executeBatch(ctx, sqls);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _executeBatch(Context ctx, List sqls) throws BOSException;

    public void executeBatch(Context ctx, String sql, List params) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("33fbda2f-0117-1000-e000-0003c0a81296"), new Object[]{ctx, sql, params});
            invokeServiceBefore(svcCtx);
            _executeBatch(ctx, sql, params);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _executeBatch(Context ctx, String sql, List params) throws BOSException;

}