package com.kingdee.eas.fdc.contract.app;

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
import java.util.List;



public abstract class AbstractClearSplitFacadeControllerBean extends AbstractBizControllerBean implements ClearSplitFacadeController
{
    protected AbstractClearSplitFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("E4771802");
    }

    public void clearAllSplit(Context ctx, String contractID, boolean isContract) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9ec77f6c-0116-1000-e000-0002c0a812cd"), new Object[]{ctx, contractID, new Boolean(isContract)});
            invokeServiceBefore(svcCtx);
            _clearAllSplit(ctx, contractID, isContract);
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
    protected abstract void _clearAllSplit(Context ctx, String contractID, boolean isContract) throws BOSException, EASBizException;

    public void clearSplitBill(Context ctx, String contractID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9ec77f6c-0116-1000-e000-0004c0a812cd"), new Object[]{ctx, contractID});
            invokeServiceBefore(svcCtx);
            _clearSplitBill(ctx, contractID);
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
    protected abstract void _clearSplitBill(Context ctx, String contractID) throws BOSException, EASBizException;

    public void clearNoTextSplit(Context ctx, String id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("af87d44d-0117-1000-e000-0002c0a812cd"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            _clearNoTextSplit(ctx, id);
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
    protected abstract void _clearNoTextSplit(Context ctx, String id) throws BOSException, EASBizException;

    public void clearSettle(Context ctx, String contractId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4f669fe3-0118-1000-e000-0083c0a812a1"), new Object[]{ctx, contractId});
            invokeServiceBefore(svcCtx);
            _clearSettle(ctx, contractId);
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
    protected abstract void _clearSettle(Context ctx, String contractId) throws BOSException, EASBizException;

    public void clearWithoutTextSplit(Context ctx, String id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7c66cfbe-0118-1000-e000-0002c0a812a1"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            _clearWithoutTextSplit(ctx, id);
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
    protected abstract void _clearWithoutTextSplit(Context ctx, String id) throws BOSException, EASBizException;

    public void clearPaymentSplit(Context ctx, List idList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("81b0c3b0-0118-1000-e000-0037c0a812a1"), new Object[]{ctx, idList});
            invokeServiceBefore(svcCtx);
            _clearPaymentSplit(ctx, idList);
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
    protected abstract void _clearPaymentSplit(Context ctx, List idList) throws BOSException, EASBizException;

    public String clearPeriodConSplit(Context ctx, String contractID, String type) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("319ae0a2-0119-1000-e000-0003c0a812a1"), new Object[]{ctx, contractID, type});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_clearPeriodConSplit(ctx, contractID, type);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
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
    protected abstract String _clearPeriodConSplit(Context ctx, String contractID, String type) throws BOSException, EASBizException;

    public void clearChangeSplit(Context ctx, String changeId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3b159017-0119-1000-e000-0003c0a812a1"), new Object[]{ctx, changeId});
            invokeServiceBefore(svcCtx);
            _clearChangeSplit(ctx, changeId);
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
    protected abstract void _clearChangeSplit(Context ctx, String changeId) throws BOSException, EASBizException;

    public List getToInvalidContract(Context ctx, List idList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9269faa1-0119-1000-e000-0003c0a812a1"), new Object[]{ctx, idList});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_getToInvalidContract(ctx, idList);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract List _getToInvalidContract(Context ctx, List idList) throws BOSException, EASBizException;

}