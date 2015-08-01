package com.kingdee.eas.fdc.finance.app;

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

import com.kingdee.eas.framework.BillEntryBaseCollection;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanDetailEntryInfo;
import com.kingdee.eas.framework.CoreBillEntryBaseCollection;
import com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanDetailEntryCollection;
import com.kingdee.eas.fdc.finance.PayPlanDataBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;



public abstract class AbstractProjectDynaticFundPayPlanDetailEntryControllerBean extends PayPlanDataBaseControllerBean implements ProjectDynaticFundPayPlanDetailEntryController
{
    protected AbstractProjectDynaticFundPayPlanDetailEntryControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("04F78CB1");
    }

    public ProjectDynaticFundPayPlanDetailEntryInfo getProjectDynaticFundPayPlanDetailEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fc9a0d33-021f-42c8-8f66-9a783fd2a342"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            ProjectDynaticFundPayPlanDetailEntryInfo retValue = (ProjectDynaticFundPayPlanDetailEntryInfo)_getValue(ctx, pk);
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
    protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk);
    }

    public ProjectDynaticFundPayPlanDetailEntryInfo getProjectDynaticFundPayPlanDetailEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3f694017-567c-4e1f-a9aa-fe3cc026e4dc"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            ProjectDynaticFundPayPlanDetailEntryInfo retValue = (ProjectDynaticFundPayPlanDetailEntryInfo)_getValue(ctx, pk, selector);
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
    protected IObjectValue _getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk, selector);
    }

    public ProjectDynaticFundPayPlanDetailEntryInfo getProjectDynaticFundPayPlanDetailEntryInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f99ca697-9b78-4016-80cc-4550298c11be"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            ProjectDynaticFundPayPlanDetailEntryInfo retValue = (ProjectDynaticFundPayPlanDetailEntryInfo)_getValue(ctx, oql);
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
    protected IObjectValue _getValue(Context ctx, String oql) throws BOSException, EASBizException
    {
        return super._getValue(ctx, oql);
    }

    public ProjectDynaticFundPayPlanDetailEntryCollection getProjectDynaticFundPayPlanDetailEntryCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("017fce38-6bb0-4819-803b-b713f2b4ce78"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            ProjectDynaticFundPayPlanDetailEntryCollection retValue = (ProjectDynaticFundPayPlanDetailEntryCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx) throws BOSException
    {
        return super._getCollection(ctx, svcCtx);
    }

    public ProjectDynaticFundPayPlanDetailEntryCollection getProjectDynaticFundPayPlanDetailEntryCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d03cd546-ea58-454f-af1d-ebb76dee9dd4"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            ProjectDynaticFundPayPlanDetailEntryCollection retValue = (ProjectDynaticFundPayPlanDetailEntryCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, EntityViewInfo view) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, view);
    }

    public ProjectDynaticFundPayPlanDetailEntryCollection getProjectDynaticFundPayPlanDetailEntryCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("737bfbdc-b95c-4ed6-85cf-acdf621fb063"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            ProjectDynaticFundPayPlanDetailEntryCollection retValue = (ProjectDynaticFundPayPlanDetailEntryCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, String oql) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, oql);
    }

    public PayPlanDataBaseCollection getPayPlanDataBaseCollection (Context ctx) throws BOSException
    {
    	return (PayPlanDataBaseCollection)(getProjectDynaticFundPayPlanDetailEntryCollection(ctx).cast(PayPlanDataBaseCollection.class));
    }
    public PayPlanDataBaseCollection getPayPlanDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (PayPlanDataBaseCollection)(getProjectDynaticFundPayPlanDetailEntryCollection(ctx, view).cast(PayPlanDataBaseCollection.class));
    }
    public PayPlanDataBaseCollection getPayPlanDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (PayPlanDataBaseCollection)(getProjectDynaticFundPayPlanDetailEntryCollection(ctx, oql).cast(PayPlanDataBaseCollection.class));
    }
    public BillEntryBaseCollection getBillEntryBaseCollection (Context ctx) throws BOSException
    {
    	return (BillEntryBaseCollection)(getProjectDynaticFundPayPlanDetailEntryCollection(ctx).cast(BillEntryBaseCollection.class));
    }
    public BillEntryBaseCollection getBillEntryBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (BillEntryBaseCollection)(getProjectDynaticFundPayPlanDetailEntryCollection(ctx, view).cast(BillEntryBaseCollection.class));
    }
    public BillEntryBaseCollection getBillEntryBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (BillEntryBaseCollection)(getProjectDynaticFundPayPlanDetailEntryCollection(ctx, oql).cast(BillEntryBaseCollection.class));
    }
    public CoreBillEntryBaseCollection getCoreBillEntryBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillEntryBaseCollection)(getProjectDynaticFundPayPlanDetailEntryCollection(ctx).cast(CoreBillEntryBaseCollection.class));
    }
    public CoreBillEntryBaseCollection getCoreBillEntryBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillEntryBaseCollection)(getProjectDynaticFundPayPlanDetailEntryCollection(ctx, view).cast(CoreBillEntryBaseCollection.class));
    }
    public CoreBillEntryBaseCollection getCoreBillEntryBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillEntryBaseCollection)(getProjectDynaticFundPayPlanDetailEntryCollection(ctx, oql).cast(CoreBillEntryBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectDynaticFundPayPlanDetailEntryCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectDynaticFundPayPlanDetailEntryCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectDynaticFundPayPlanDetailEntryCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}