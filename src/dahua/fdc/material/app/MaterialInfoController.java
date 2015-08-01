package com.kingdee.eas.fdc.material.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.material.MaterialInfoCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.math.BigDecimal;
import java.util.Map;
import com.kingdee.bos.ctrl.freechart.data.xy.XYDataset;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.material.MaterialInfoInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MaterialInfoController extends FDCBillController
{
    public MaterialInfoInfo getValue(Context ctx, String sql, List params) throws BOSException, RemoteException;
    public MaterialInfoInfo getMaterialInfoInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MaterialInfoInfo getMaterialInfoInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public MaterialInfoCollection getMaterialInfoCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public MaterialInfoCollection getMaterialInfoCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MaterialInfoCollection getMaterialInfoCollection(Context ctx) throws BOSException, RemoteException;
    public void addMaterialData(Context ctx, List params, String sql) throws BOSException, EASBizException, RemoteException;
    public List getMaterialData(Context ctx, Map filters, String tableName, List fields) throws BOSException, EASBizException, RemoteException;
    public String getBOSID(Context ctx, String bosType) throws BOSException, EASBizException, RemoteException;
    public String getMaterialID(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public Map getMaterialInfoMsg(Context ctx, IObjectPK materialID) throws BOSException, RemoteException;
    public List getPriceAndDateAndSupplierByMaterialId(Context ctx, String materialId) throws BOSException, EASBizException, RemoteException;
    public List getLastestQuoteTime(Context ctx) throws BOSException, EASBizException, RemoteException;
    public void updateIsLatest(Context ctx, String materialInfoId) throws BOSException, EASBizException, RemoteException;
    public IRowSet selectTraitIndex(Context ctx, String traitIndex, IObjectPK tblMaterialID) throws BOSException, EASBizException, RemoteException;
    public IRowSet selectTraitAndSuperIndex(Context ctx, String materialGroupLongNumber) throws BOSException, EASBizException, RemoteException;
    public IRowSet requiredFields(Context ctx, String supplier, BigDecimal price, String pkQuoteTime, String materialId) throws BOSException, EASBizException, RemoteException;
    public void deleteMaterialInfoRecord(Context ctx, IObjectPK ID) throws BOSException, EASBizException, RemoteException;
    public XYDataset getLineDataSet(Context ctx, String name, String materalId) throws BOSException, RemoteException;
    public XYDataset getDotDataSet(Context ctx, String materialName, String materalId) throws BOSException, RemoteException;
    public Set getMaterialInfo(Context ctx, IObjectPK materialId) throws BOSException, RemoteException;
    public int updateTraitIndexValue(Context ctx, String id, String value) throws BOSException, EASBizException, RemoteException;
    public IRowSet selectMaterialIndexValue(Context ctx, String traitIndex, IObjectPK tblMaterialID) throws BOSException, EASBizException, RemoteException;
    public Set getMaterialIndexAndValue(Context ctx, IObjectPK materialId) throws BOSException, RemoteException;
    public int deleteMaterialIndexValue(Context ctx, String materialInfoId, String materialIndexId) throws BOSException, EASBizException, RemoteException;
}