package com.kingdee.eas.fdc.material.app;

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
import com.kingdee.eas.fdc.material.MaterialInfoCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.math.BigDecimal;
import com.kingdee.bos.ctrl.freechart.data.xy.XYDataset;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.material.MaterialInfoInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import java.util.List;



public abstract class AbstractMaterialInfoControllerBean extends FDCBillControllerBean implements MaterialInfoController
{
    protected AbstractMaterialInfoControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("9D390CBB");
    }

    public MaterialInfoInfo getValue(Context ctx, String sql, List params) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9017b83a-8295-4a68-bb43-c218b93cb69d"), new Object[]{ctx, sql, params});
            invokeServiceBefore(svcCtx);
            MaterialInfoInfo retValue = (MaterialInfoInfo)_getValue(ctx, sql, params);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, String sql, List params) throws BOSException
    {    	
        return null;
    }

    public MaterialInfoInfo getMaterialInfoInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ff139e7d-337b-4dfc-a14e-78e6ba2bffa2"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            MaterialInfoInfo retValue = (MaterialInfoInfo)_getValue(ctx, pk, selector);
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

    public MaterialInfoInfo getMaterialInfoInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c1bf3192-7e0f-4fc5-80cf-bbd0b2c7b54c"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            MaterialInfoInfo retValue = (MaterialInfoInfo)_getValue(ctx, oql);
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

    public MaterialInfoCollection getMaterialInfoCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("42df4cd3-fc4d-4ac8-9e28-1e04e2135550"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            MaterialInfoCollection retValue = (MaterialInfoCollection)_getCollection(ctx, svcCtx, oql);
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

    public MaterialInfoCollection getMaterialInfoCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("187d4604-1821-43e4-b34c-e4b8ec755590"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            MaterialInfoCollection retValue = (MaterialInfoCollection)_getCollection(ctx, svcCtx, view);
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

    public MaterialInfoCollection getMaterialInfoCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("470eae7d-3518-4b47-b6f5-d632b61d4bf4"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            MaterialInfoCollection retValue = (MaterialInfoCollection)_getCollection(ctx, svcCtx);
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

    public void addMaterialData(Context ctx, List params, String sql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("248dba0f-78e6-4a52-938e-92d1ac0d4989"), new Object[]{ctx, params, sql});
            invokeServiceBefore(svcCtx);
            _addMaterialData(ctx, params, sql);
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
    protected void _addMaterialData(Context ctx, List params, String sql) throws BOSException, EASBizException
    {    	
        return;
    }

    public List getMaterialData(Context ctx, Map filters, String tableName, List fields) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6ee11a0a-0699-4a6e-bfdd-d3412d5c77ad"), new Object[]{ctx, filters, tableName, fields});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_getMaterialData(ctx, filters, tableName, fields);
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
    protected abstract List _getMaterialData(Context ctx, Map filters, String tableName, List fields) throws BOSException, EASBizException;

    public String getBOSID(Context ctx, String bosType) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("afd8b6a2-796d-4a4f-9e95-ef1479d940fc"), new Object[]{ctx, bosType});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_getBOSID(ctx, bosType);
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
    protected abstract String _getBOSID(Context ctx, String bosType) throws BOSException, EASBizException;

    public String getMaterialID(Context ctx, String id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e807881f-4dc8-4c20-9e28-459336e69fda"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            String retValue = (String)_getMaterialID(ctx, id);
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
    protected abstract String _getMaterialID(Context ctx, String id) throws BOSException, EASBizException;

    public Map getMaterialInfoMsg(Context ctx, IObjectPK materialID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("db22ce55-af29-4d36-a5fe-b1c6810c2e49"), new Object[]{ctx, materialID});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getMaterialInfoMsg(ctx, materialID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Map _getMaterialInfoMsg(Context ctx, IObjectPK materialID) throws BOSException
    {    	
        return null;
    }

    public List getPriceAndDateAndSupplierByMaterialId(Context ctx, String materialId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2cac468f-0144-4636-8ef7-d358c7a8b12e"), new Object[]{ctx, materialId});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_getPriceAndDateAndSupplierByMaterialId(ctx, materialId);
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
    protected abstract List _getPriceAndDateAndSupplierByMaterialId(Context ctx, String materialId) throws BOSException, EASBizException;

    public List getLastestQuoteTime(Context ctx) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7d5c9ef7-b26c-460e-b6ed-f725054fca87"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_getLastestQuoteTime(ctx);
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
    protected abstract List _getLastestQuoteTime(Context ctx) throws BOSException, EASBizException;

    public void updateIsLatest(Context ctx, String materialInfoId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("43ed30b4-4a95-4b12-9f8c-2a86e4f45fb3"), new Object[]{ctx, materialInfoId});
            invokeServiceBefore(svcCtx);
            _updateIsLatest(ctx, materialInfoId);
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
    protected abstract void _updateIsLatest(Context ctx, String materialInfoId) throws BOSException, EASBizException;

    public IRowSet selectTraitIndex(Context ctx, String traitIndex, IObjectPK tblMaterialID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("06855dcc-4f4a-4b99-862b-5f259aab0746"), new Object[]{ctx, traitIndex, tblMaterialID});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_selectTraitIndex(ctx, traitIndex, tblMaterialID);
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
    protected abstract IRowSet _selectTraitIndex(Context ctx, String traitIndex, IObjectPK tblMaterialID) throws BOSException, EASBizException;

    public IRowSet selectTraitAndSuperIndex(Context ctx, String materialGroupLongNumber) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f22e28f3-d0bc-4d0e-8e18-6253253957c5"), new Object[]{ctx, materialGroupLongNumber});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_selectTraitAndSuperIndex(ctx, materialGroupLongNumber);
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
    protected abstract IRowSet _selectTraitAndSuperIndex(Context ctx, String materialGroupLongNumber) throws BOSException, EASBizException;

    public IRowSet requiredFields(Context ctx, String supplier, BigDecimal price, String pkQuoteTime, String materialId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("56646630-8a67-4a21-a878-32fd7f4cfef5"), new Object[]{ctx, supplier, price, pkQuoteTime, materialId});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_requiredFields(ctx, supplier, price, pkQuoteTime, materialId);
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
    protected abstract IRowSet _requiredFields(Context ctx, String supplier, BigDecimal price, String pkQuoteTime, String materialId) throws BOSException, EASBizException;

    public void deleteMaterialInfoRecord(Context ctx, IObjectPK ID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("14bc60e9-d645-46f4-beab-a29696b5482e"), new Object[]{ctx, ID});
            invokeServiceBefore(svcCtx);
            _deleteMaterialInfoRecord(ctx, ID);
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
    protected abstract void _deleteMaterialInfoRecord(Context ctx, IObjectPK ID) throws BOSException, EASBizException;

    public XYDataset getLineDataSet(Context ctx, String name, String materalId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e6549b62-0cab-47eb-9014-6793ea0ebe2c"), new Object[]{ctx, name, materalId});
            invokeServiceBefore(svcCtx);
            XYDataset retValue = (XYDataset)_getLineDataSet(ctx, name, materalId);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract XYDataset _getLineDataSet(Context ctx, String name, String materalId) throws BOSException;

    public XYDataset getDotDataSet(Context ctx, String materialName, String materalId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c3f3de84-200a-4d8d-b103-80e3fb609a50"), new Object[]{ctx, materialName, materalId});
            invokeServiceBefore(svcCtx);
            XYDataset retValue = (XYDataset)_getDotDataSet(ctx, materialName, materalId);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract XYDataset _getDotDataSet(Context ctx, String materialName, String materalId) throws BOSException;

    public Set getMaterialInfo(Context ctx, IObjectPK materialId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f4d97689-1dfe-466e-afb7-0278985ececd"), new Object[]{ctx, materialId});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getMaterialInfo(ctx, materialId);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Set _getMaterialInfo(Context ctx, IObjectPK materialId) throws BOSException
    {    	
        return null;
    }

    public int updateTraitIndexValue(Context ctx, String id, String value) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("68532039-0ea4-41ed-922d-738d06eafff2"), new Object[]{ctx, id, value});
            invokeServiceBefore(svcCtx);
            int retValue = (int)_updateTraitIndexValue(ctx, id, value);
            svcCtx.setMethodReturnValue(new Integer(retValue));
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
    protected abstract int _updateTraitIndexValue(Context ctx, String id, String value) throws BOSException, EASBizException;

    public IRowSet selectMaterialIndexValue(Context ctx, String traitIndex, IObjectPK tblMaterialID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a00e61d8-1697-4561-851d-eae69094c1e1"), new Object[]{ctx, traitIndex, tblMaterialID});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_selectMaterialIndexValue(ctx, traitIndex, tblMaterialID);
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
    protected abstract IRowSet _selectMaterialIndexValue(Context ctx, String traitIndex, IObjectPK tblMaterialID) throws BOSException, EASBizException;

    public Set getMaterialIndexAndValue(Context ctx, IObjectPK materialId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8dc34df1-a25b-4b87-9315-6f7f1242c27f"), new Object[]{ctx, materialId});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getMaterialIndexAndValue(ctx, materialId);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected Set _getMaterialIndexAndValue(Context ctx, IObjectPK materialId) throws BOSException
    {    	
        return null;
    }

    public int deleteMaterialIndexValue(Context ctx, String materialInfoId, String materialIndexId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4324dbee-0731-4fcc-8359-19e90912a8b8"), new Object[]{ctx, materialInfoId, materialIndexId});
            invokeServiceBefore(svcCtx);
            int retValue = (int)_deleteMaterialIndexValue(ctx, materialInfoId, materialIndexId);
            svcCtx.setMethodReturnValue(new Integer(retValue));
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
    protected abstract int _deleteMaterialIndexValue(Context ctx, String materialInfoId, String materialIndexId) throws BOSException, EASBizException;

    public FDCBillCollection getFDCBillCollection (Context ctx) throws BOSException
    {
    	return (FDCBillCollection)(getMaterialInfoCollection(ctx).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCBillCollection)(getMaterialInfoCollection(ctx, view).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCBillCollection)(getMaterialInfoCollection(ctx, oql).cast(FDCBillCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getMaterialInfoCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getMaterialInfoCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getMaterialInfoCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getMaterialInfoCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getMaterialInfoCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getMaterialInfoCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getMaterialInfoCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getMaterialInfoCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getMaterialInfoCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}