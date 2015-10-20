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
import java.util.Map;
import java.util.Set;



public abstract class AbstractContractExecFacadeControllerBean extends AbstractBizControllerBean implements ContractExecFacadeController
{
    protected AbstractContractExecFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("4014ACB8");
    }

    public Map _getCompleteAmt(Context ctx, String fullOrgUnitID, Set idSet) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("09fe30b9-e7a1-40ac-90f5-00d4d5d3dd9f"), new Object[]{ctx, fullOrgUnitID, idSet});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)__getCompleteAmt(ctx, fullOrgUnitID, idSet);
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
    protected abstract Map __getCompleteAmt(Context ctx, String fullOrgUnitID, Set idSet) throws BOSException, EASBizException;

    public Map _getCompletePrjAmtForNoTextContract(Context ctx, Set idSet) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ce1bd35b-b7d8-4e73-83e0-11aa23f0fda3"), new Object[]{ctx, idSet});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)__getCompletePrjAmtForNoTextContract(ctx, idSet);
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
    protected abstract Map __getCompletePrjAmtForNoTextContract(Context ctx, Set idSet) throws BOSException, EASBizException;

    public Map getCompleteAmt(Context ctx, Map orgId2ContractIdSet) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5c5aeaa6-af97-4609-bc93-d131e9dbd954"), new Object[]{ctx, orgId2ContractIdSet});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getCompleteAmt(ctx, orgId2ContractIdSet);
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
    protected abstract Map _getCompleteAmt(Context ctx, Map orgId2ContractIdSet) throws BOSException, EASBizException;

    public Map getAllInvoiceAmt(Context ctx, Set idSet) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2dc1a738-08e6-42fb-8f45-a39ee94757d2"), new Object[]{ctx, idSet});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getAllInvoiceAmt(ctx, idSet);
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
    protected abstract Map _getAllInvoiceAmt(Context ctx, Set idSet) throws BOSException, EASBizException;

    public Map getAllInvoiceOriAmt(Context ctx, Set idSet) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a6768308-b5dc-47b6-b5c0-f36e8a61ea21"), new Object[]{ctx, idSet});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getAllInvoiceOriAmt(ctx, idSet);
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
    protected abstract Map _getAllInvoiceOriAmt(Context ctx, Set idSet) throws BOSException, EASBizException;

    public Map getAdjustSumMap(Context ctx, Set idSet) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("42c2705a-f0f7-4e26-a8a1-96762bf37802"), new Object[]{ctx, idSet});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getAdjustSumMap(ctx, idSet);
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
    protected abstract Map _getAdjustSumMap(Context ctx, Set idSet) throws BOSException, EASBizException;

    public Map getInvoiceAmt(Context ctx, Set idSet) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1aa258eb-1cdd-409a-9834-94d7fbeb7dcd"), new Object[]{ctx, idSet});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getInvoiceAmt(ctx, idSet);
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
    protected abstract Map _getInvoiceAmt(Context ctx, Set idSet) throws BOSException, EASBizException;

    public Map getInvoiceOriAmt(Context ctx, Set idSet) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ce7a9c9c-8fbd-4063-9db9-07f16eb73bc2"), new Object[]{ctx, idSet});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getInvoiceOriAmt(ctx, idSet);
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
    protected abstract Map _getInvoiceOriAmt(Context ctx, Set idSet) throws BOSException, EASBizException;

}