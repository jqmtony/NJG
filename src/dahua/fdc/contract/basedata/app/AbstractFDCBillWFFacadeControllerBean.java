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
import java.util.Map;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import java.util.Set;



public abstract class AbstractFDCBillWFFacadeControllerBean extends AbstractBizControllerBean implements FDCBillWFFacadeController
{
    protected AbstractFDCBillWFFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("06611023");
    }

    public void setWFAuditNotPrint(Context ctx, BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("22ae2e99-011d-1000-e000-0091c0a810b3"), new Object[]{ctx, billId, auditorId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _setWFAuditNotPrint(ctx, billId, auditorId);
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
    protected abstract void _setWFAuditNotPrint(Context ctx, BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException;

    public void setWFAuditOrgInfo(Context ctx, BOSUuid billId, BOSUuid auditorId, String org) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("22ae2e99-011d-1000-e000-0094c0a810b3"), new Object[]{ctx, billId, auditorId, org});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _setWFAuditOrgInfo(ctx, billId, auditorId, org);
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
    protected abstract void _setWFAuditOrgInfo(Context ctx, BOSUuid billId, BOSUuid auditorId, String org) throws BOSException, EASBizException;

    public List getWFAuditResultForPrint(Context ctx, String billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("22ae2e99-011d-1000-e000-01d4c0a810b3"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            List retValue = (List)_getWFAuditResultForPrint(ctx, billId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (List)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract List _getWFAuditResultForPrint(Context ctx, String billId) throws BOSException, EASBizException;

    public List getWFAuditResultForPrint2(Context ctx, String billId, boolean showHistory) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("566d615c-dd66-4741-9513-94e1c9575dc3"), new Object[]{ctx, billId, new Boolean(showHistory)});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            List retValue = (List)_getWFAuditResultForPrint2(ctx, billId, showHistory);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (List)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract List _getWFAuditResultForPrint2(Context ctx, String billId, boolean showHistory) throws BOSException, EASBizException;

    public Map getWFBillLastAuditorAndTime(Context ctx, Set billIds) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("22ae2e99-011d-1000-e000-02b1c0a810b3"), new Object[]{ctx, billIds});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getWFBillLastAuditorAndTime(ctx, billIds);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getWFBillLastAuditorAndTime(Context ctx, Set billIds) throws BOSException, EASBizException;

}