package com.kingdee.eas.fdc.material;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.material.app.*;
import java.util.Map;
import java.math.BigDecimal;
import com.kingdee.bos.ctrl.freechart.data.xy.XYDataset;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.List;

public class MaterialInfo extends FDCBill implements IMaterialInfo
{
    public MaterialInfo()
    {
        super();
        registerInterface(IMaterialInfo.class, this);
    }
    public MaterialInfo(Context ctx)
    {
        super(ctx);
        registerInterface(IMaterialInfo.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9D390CBB");
    }
    private MaterialInfoController getController() throws BOSException
    {
        return (MaterialInfoController)getBizController();
    }
    /**
     *getValue-User defined method
     *@param sql sql
     *@param params params
     *@return
     */
    public MaterialInfoInfo getValue(String sql, List params) throws BOSException
    {
        try {
            return getController().getValue(getContext(), sql, params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public MaterialInfoInfo getMaterialInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialInfoInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public MaterialInfoInfo getMaterialInfoInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialInfoInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public MaterialInfoCollection getMaterialInfoCollection(String oql) throws BOSException
    {
        try {
            return getController().getMaterialInfoCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public MaterialInfoCollection getMaterialInfoCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMaterialInfoCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MaterialInfoCollection getMaterialInfoCollection() throws BOSException
    {
        try {
            return getController().getMaterialInfoCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *材料信息-User defined method
     *@param params params
     *@param sql sql
     */
    public void addMaterialData(List params, String sql) throws BOSException, EASBizException
    {
        try {
            getController().addMaterialData(getContext(), params, sql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *查询-User defined method
     *@param filters 过滤的条件
     *@param tableName 表名
     *@param fields 字段
     *@return
     */
    public List getMaterialData(Map filters, String tableName, List fields) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialData(getContext(), filters, tableName, fields);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得UUID-User defined method
     *@param bosType bosType
     *@return
     */
    public String getBOSID(String bosType) throws BOSException, EASBizException
    {
        try {
            return getController().getBOSID(getContext(), bosType);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得物料id-User defined method
     *@param id id
     *@return
     */
    public String getMaterialID(String id) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialID(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得物料详细信息-User defined method
     *@param materialID materialID
     *@return
     */
    public Map getMaterialInfoMsg(IObjectPK materialID) throws BOSException
    {
        try {
            return getController().getMaterialInfoMsg(getContext(), materialID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据物料id，查询单价、日期、供应商-User defined method
     *@param materialId materialId
     *@return
     */
    public List getPriceAndDateAndSupplierByMaterialId(String materialId) throws BOSException, EASBizException
    {
        try {
            return getController().getPriceAndDateAndSupplierByMaterialId(getContext(), materialId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得最新报价时间-User defined method
     *@return
     */
    public List getLastestQuoteTime() throws BOSException, EASBizException
    {
        try {
            return getController().getLastestQuoteTime(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置最新材料信息-User defined method
     *@param materialInfoId materialInfoId
     */
    public void updateIsLatest(String materialInfoId) throws BOSException, EASBizException
    {
        try {
            getController().updateIsLatest(getContext(), materialInfoId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *查询特性指标-User defined method
     *@param traitIndex traitIndex
     *@param tblMaterialID tblMaterialID
     *@return
     */
    public IRowSet selectTraitIndex(String traitIndex, IObjectPK tblMaterialID) throws BOSException, EASBizException
    {
        try {
            return getController().selectTraitIndex(getContext(), traitIndex, tblMaterialID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得特性指标和上级指标-User defined method
     *@param materialGroupLongNumber 材料组的长级码
     *@return
     */
    public IRowSet selectTraitAndSuperIndex(String materialGroupLongNumber) throws BOSException, EASBizException
    {
        try {
            return getController().selectTraitAndSuperIndex(getContext(), materialGroupLongNumber);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *查看待插入的信息是否已存在-User defined method
     *@param supplier supplier
     *@param price price
     *@param pkQuoteTime pkQuoteTime
     *@param materialId materialId
     *@return
     */
    public IRowSet requiredFields(String supplier, BigDecimal price, String pkQuoteTime, String materialId) throws BOSException, EASBizException
    {
        try {
            return getController().requiredFields(getContext(), supplier, price, pkQuoteTime, materialId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *deleteMaterialInfoRecord-User defined method
     *@param ID ID
     */
    public void deleteMaterialInfoRecord(IObjectPK ID) throws BOSException, EASBizException
    {
        try {
            getController().deleteMaterialInfoRecord(getContext(), ID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getLineDataSet-User defined method
     *@param name name
     *@param materalId materalId
     *@return
     */
    public XYDataset getLineDataSet(String name, String materalId) throws BOSException
    {
        try {
            return getController().getLineDataSet(getContext(), name, materalId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getDotDataSet-User defined method
     *@param materialName materialName
     *@param materalId materalId
     *@return
     */
    public XYDataset getDotDataSet(String materialName, String materalId) throws BOSException
    {
        try {
            return getController().getDotDataSet(getContext(), materialName, materalId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得材料消息信息-User defined method
     *@param materialId materialId
     *@return
     */
    public Set getMaterialInfo(IObjectPK materialId) throws BOSException
    {
        try {
            return getController().getMaterialInfo(getContext(), materialId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新特性指标-User defined method
     *@param id id
     *@param value value
     *@return
     */
    public int updateTraitIndexValue(String id, String value) throws BOSException, EASBizException
    {
        try {
            return getController().updateTraitIndexValue(getContext(), id, value);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *查询特性指标值-User defined method
     *@param traitIndex traitIndex
     *@param tblMaterialID tblMaterialID
     *@return
     */
    public IRowSet selectMaterialIndexValue(String traitIndex, IObjectPK tblMaterialID) throws BOSException, EASBizException
    {
        try {
            return getController().selectMaterialIndexValue(getContext(), traitIndex, tblMaterialID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得特性指标和值-User defined method
     *@param materialId 物料ID
     *@return
     */
    public Set getMaterialIndexAndValue(IObjectPK materialId) throws BOSException
    {
        try {
            return getController().getMaterialIndexAndValue(getContext(), materialId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据materialId和-User defined method
     *@param materialInfoId materialInfoId
     *@param materialIndexId materialIndexId
     *@return
     */
    public int deleteMaterialIndexValue(String materialInfoId, String materialIndexId) throws BOSException, EASBizException
    {
        try {
            return getController().deleteMaterialIndexValue(getContext(), materialInfoId, materialIndexId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}