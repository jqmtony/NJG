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
import com.kingdee.eas.common.EASBizException;
import java.lang.Object;
import java.util.Map;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;



public abstract class AbstractFDCBillFacadeControllerBean extends AbstractBizControllerBean implements FDCBillFacadeController
{
    protected AbstractFDCBillFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("5138CB14");
    }

    public void autoChangeSettle(Context ctx, String settleId, boolean isAll) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("436bd8c0-0117-1000-e000-0003c0a81296"), new Object[]{ctx, settleId, new Boolean(isAll)});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _autoChangeSettle(ctx, settleId, isAll);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _autoChangeSettle(Context ctx, String settleId, boolean isAll) throws BOSException, EASBizException;

    public Object execAction(Context ctx, Map param) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3a8908a5-0118-1000-e000-0002c0a81296"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Object retValue = (Object)_execAction(ctx, param);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Object)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Object _execAction(Context ctx, Map param) throws BOSException;

    public FDCSplitBillInfo autoPropSplit(Context ctx, String type, Map dataMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9405bd5f-0119-1000-e000-0058c0a812a0"), new Object[]{ctx, type, dataMap});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            FDCSplitBillInfo retValue = (FDCSplitBillInfo)_autoPropSplit(ctx, type, dataMap);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (FDCSplitBillInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectValue _autoPropSplit(Context ctx, String type, Map dataMap) throws BOSException, EASBizException;

    public void setBillAudited4wf(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e889b1d8-2494-4863-a521-71973f4cb05a"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _setBillAudited4wf(ctx, billId);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setBillAudited4wf(Context ctx, BOSUuid billId) throws BOSException, EASBizException;

    public void setBillAudited4wf2(Context ctx, BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ccc0bfc5-71be-4498-af52-d96f4f1d9f3f"), new Object[]{ctx, billId, auditorId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _setBillAudited4wf2(ctx, billId, auditorId);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setBillAudited4wf2(Context ctx, BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException;

    public void setBillAuditing4wf2(Context ctx, BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a35cbf29-fcfa-42f4-9f1f-9e5fce6b520b"), new Object[]{ctx, billId, auditorId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _setBillAuditing4wf2(ctx, billId, auditorId);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _setBillAuditing4wf2(Context ctx, BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException;

    public void dealSaveAboutConChange(Context ctx, String settleId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0389cb9f-4d25-4bff-aff4-f9ea80082081"), new Object[]{ctx, settleId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _dealSaveAboutConChange(ctx, settleId);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _dealSaveAboutConChange(Context ctx, String settleId) throws BOSException, EASBizException;

}