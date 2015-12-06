package com.kingdee.eas.fdc.earlywarn;

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



public abstract class AbstractDHWarnMsgFacadeControllerBean extends AbstractBizControllerBean implements DHWarnMsgFacadeController
{
    protected AbstractDHWarnMsgFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("90D6137E");
    }

    public void programmingWarnMsg(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ae6aef2e-29a6-4a64-bdcf-f4171fa43fe6"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _programmingWarnMsg(ctx);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _programmingWarnMsg(Context ctx) throws BOSException
    {    	
        return;
    }

    public void dhScheduleWarnMsg(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("85a3136a-5e58-492d-baa1-acb9c64a0ee8"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _dhScheduleWarnMsg(ctx);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _dhScheduleWarnMsg(Context ctx) throws BOSException
    {    	
        return;
    }

    public void programmingGZWarnMsg(Context ctx, String billId, int day) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9fc33a35-6362-4d3f-8977-3f6774732041"), new Object[]{ctx, billId, new Integer(day)});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _programmingGZWarnMsg(ctx, billId, day);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _programmingGZWarnMsg(Context ctx, String billId, int day) throws BOSException
    {    	
        return;
    }

    public void settleDeclarationWarnMsg(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("612daa8c-c507-4fd5-975b-db1455d29b5e"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _settleDeclarationWarnMsg(ctx);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _settleDeclarationWarnMsg(Context ctx) throws BOSException
    {    	
        return;
    }

    public void aimCostDiffWarnMsg(Context ctx, String billId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1187e0d8-2db5-41f8-8919-d52de034f0a0"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _aimCostDiffWarnMsg(ctx, billId);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _aimCostDiffWarnMsg(Context ctx, String billId) throws BOSException
    {    	
        return;
    }

}