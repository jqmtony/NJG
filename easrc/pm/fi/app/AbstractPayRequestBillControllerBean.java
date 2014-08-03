package com.kingdee.eas.port.pm.fi.app;

import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.port.pm.fi.PayRequestBillCollection;
import com.kingdee.eas.port.pm.fi.PayRequestBillFactory;
import com.kingdee.eas.port.pm.fi.PayRequestBillInfo;



public abstract class AbstractPayRequestBillControllerBean extends FDCBillControllerBean implements PayRequestBillController
{
    protected AbstractPayRequestBillControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("9C9C367D");
    }

    public PayRequestBillInfo getPayRequestBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a60bda2a-397f-49fc-9b9d-bfdc5b3c7bef"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PayRequestBillInfo retValue = (PayRequestBillInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (PayRequestBillInfo)svcCtx.getMethodReturnValue();
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

    public PayRequestBillInfo getPayRequestBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e18f3abe-f458-4d9b-a3af-183534d33c6e"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PayRequestBillInfo retValue = (PayRequestBillInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (PayRequestBillInfo)svcCtx.getMethodReturnValue();
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

    public PayRequestBillInfo getPayRequestBillInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("dd1c9cfc-1aa8-427f-9681-880bba3a6567"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PayRequestBillInfo retValue = (PayRequestBillInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (PayRequestBillInfo)svcCtx.getMethodReturnValue();
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

    public PayRequestBillCollection getPayRequestBillCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6ecca48a-5c59-4761-b549-278b49a991ec"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PayRequestBillCollection retValue = (PayRequestBillCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (PayRequestBillCollection)svcCtx.getMethodReturnValue();
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

    public PayRequestBillCollection getPayRequestBillCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6c1b1121-bdfa-4dd2-8566-b6e0d4791b0a"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PayRequestBillCollection retValue = (PayRequestBillCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (PayRequestBillCollection)svcCtx.getMethodReturnValue();
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

    public PayRequestBillCollection getPayRequestBillCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fc676b44-7677-45d1-897f-eb50801a129a"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            PayRequestBillCollection retValue = (PayRequestBillCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (PayRequestBillCollection)svcCtx.getMethodReturnValue();
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

    public void audit(Context ctx, List ids) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fb7bc411-2d7e-485c-b731-7f58a5142f59"), new Object[]{ctx, ids});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _audit(ctx, ids);
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

    public void unAudit(Context ctx, List ids) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("34990d16-8ff4-4c55-b640-145252d12a9e"), new Object[]{ctx, ids});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _unAudit(ctx, ids);
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

    public void setAuditing(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("13f7fee8-73b0-4ae1-9f0a-4589a950b827"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _setAuditing(ctx, id);
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
    protected void _setAuditing(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {    	
        return;
    }

    public void setAudited(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9b9e3829-7dc2-40c0-9d46-80fef608ef76"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _setAudited(ctx, id);
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
    protected void _setAudited(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {    	
        return;
    }

    public void addDeductBill(Context ctx, IObjectValue model) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("89af0350-860c-4f70-b39c-e2ed07cc1495"), new Object[]{ctx, model});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _addDeductBill(ctx, model);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _addDeductBill(Context ctx, IObjectValue model) throws BOSException
    {    	
        return;
    }

    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0a0b5a30-fc3d-4451-a178-407f0904b633"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _audit(ctx, billId);
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

    public void unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("75ec0b70-070b-465b-a235-cc5f28ede81f"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _unAudit(ctx, billId);
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

    public String getContractTypeNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b73d74e4-6bd2-460f-8dfe-8d1a47bd64d3"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String retValue = (String)_getContractTypeNumber(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String _getContractTypeNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {    	
        return null;
    }

    public void close(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4a22965b-14b9-4eff-b859-ee4631d6fae3"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _close(ctx, pk);
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
    protected void _close(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {    	
        return;
    }

    public void unClose(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f5ee05b2-a5c0-4844-a82a-894f9617e732"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _unClose(ctx, pk);
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
    protected void _unClose(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {    	
        return;
    }

    public void adjustPayment(Context ctx, IObjectPK payRequestBillId, Map dataMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a2e5d59d-1144-4ad6-b2f8-386939db2b99"), new Object[]{ctx, payRequestBillId, dataMap});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _adjustPayment(ctx, payRequestBillId, dataMap);
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
    protected void _adjustPayment(Context ctx, IObjectPK payRequestBillId, Map dataMap) throws BOSException, EASBizException
    {    	
        return;
    }

    public boolean outPayPlan(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4e2e9bf2-af14-47da-9a52-f5685894c3c6"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_outPayPlan(ctx, pk);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            }
            invokeServiceAfter(svcCtx);
            return ((Boolean)svcCtx.getMethodReturnValue()).booleanValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected boolean _outPayPlan(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {    	
        return false;
    }

    public void deleteForContWithoutText(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5895bdad-d3c3-4bba-b30d-e65351c3cd50"), new Object[]{ctx, arrayPK});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _deleteForContWithoutText(ctx, arrayPK);
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
    protected void _deleteForContWithoutText(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException
    {    	
        return;
    }

    public boolean isOutBudget(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("27dd0eb8-3a7d-43d7-80c0-aeac6cd4831c"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_isOutBudget(ctx, pk);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            }
            invokeServiceAfter(svcCtx);
            return ((Boolean)svcCtx.getMethodReturnValue()).booleanValue();
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
    protected boolean _isOutBudget(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {    	
        return false;
    }

    public BOSUuid getPaymentBillId(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("02bbbefd-0eff-4cc2-a61c-c641b1571254"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BOSUuid retValue = (BOSUuid)_getPaymentBillId(ctx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (BOSUuid)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected BOSUuid _getPaymentBillId(Context ctx) throws BOSException
    {    	
        return null;
    }

    public BOSUuid auditAndOpenPayment(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("87049b22-afa4-4993-8f79-5c1359bcbb5d"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            BOSUuid retValue = (BOSUuid)_auditAndOpenPayment(ctx, billId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (BOSUuid)svcCtx.getMethodReturnValue();
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
    protected BOSUuid _auditAndOpenPayment(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {    	
        return null;
    }

    public void setUnAudited2Auditing(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d93110ed-451c-40df-a427-ab0249b1aeab"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _setUnAudited2Auditing(ctx, billId);
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
    protected void _setUnAudited2Auditing(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {    	
        return;
    }

    public boolean bgPass(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("633ea25c-352c-4eb1-83a7-e5d726270dd5"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_bgPass(ctx, id);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            }
            invokeServiceAfter(svcCtx);
            return ((Boolean)svcCtx.getMethodReturnValue()).booleanValue();
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
    protected boolean _bgPass(Context ctx, BOSUuid id) throws BOSException, EASBizException
    {    	
        return false;
    }

    public FDCBillCollection getFDCBillCollection (Context ctx) throws BOSException
    {
    	return (FDCBillCollection)(getPayRequestBillCollection(ctx).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCBillCollection)(getPayRequestBillCollection(ctx, view).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCBillCollection)(getPayRequestBillCollection(ctx, oql).cast(FDCBillCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getPayRequestBillCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getPayRequestBillCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getPayRequestBillCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getPayRequestBillCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getPayRequestBillCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getPayRequestBillCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getPayRequestBillCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getPayRequestBillCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getPayRequestBillCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}