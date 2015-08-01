package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.math.BigDecimal;
import com.kingdee.bos.ctrl.freechart.data.xy.XYDataset;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;
import java.util.List;

public interface IMaterialInfo extends IFDCBill
{
    public MaterialInfoInfo getValue(String sql, List params) throws BOSException;
    public MaterialInfoInfo getMaterialInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MaterialInfoInfo getMaterialInfoInfo(String oql) throws BOSException, EASBizException;
    public MaterialInfoCollection getMaterialInfoCollection(String oql) throws BOSException;
    public MaterialInfoCollection getMaterialInfoCollection(EntityViewInfo view) throws BOSException;
    public MaterialInfoCollection getMaterialInfoCollection() throws BOSException;
    public void addMaterialData(List params, String sql) throws BOSException, EASBizException;
    public List getMaterialData(Map filters, String tableName, List fields) throws BOSException, EASBizException;
    public String getBOSID(String bosType) throws BOSException, EASBizException;
    public String getMaterialID(String id) throws BOSException, EASBizException;
    public Map getMaterialInfoMsg(IObjectPK materialID) throws BOSException;
    public List getPriceAndDateAndSupplierByMaterialId(String materialId) throws BOSException, EASBizException;
    public List getLastestQuoteTime() throws BOSException, EASBizException;
    public void updateIsLatest(String materialInfoId) throws BOSException, EASBizException;
    public IRowSet selectTraitIndex(String traitIndex, IObjectPK tblMaterialID) throws BOSException, EASBizException;
    public IRowSet selectTraitAndSuperIndex(String materialGroupLongNumber) throws BOSException, EASBizException;
    public IRowSet requiredFields(String supplier, BigDecimal price, String pkQuoteTime, String materialId) throws BOSException, EASBizException;
    public void deleteMaterialInfoRecord(IObjectPK ID) throws BOSException, EASBizException;
    public XYDataset getLineDataSet(String name, String materalId) throws BOSException;
    public XYDataset getDotDataSet(String materialName, String materalId) throws BOSException;
    public Set getMaterialInfo(IObjectPK materialId) throws BOSException;
    public int updateTraitIndexValue(String id, String value) throws BOSException, EASBizException;
    public IRowSet selectMaterialIndexValue(String traitIndex, IObjectPK tblMaterialID) throws BOSException, EASBizException;
    public Set getMaterialIndexAndValue(IObjectPK materialId) throws BOSException;
    public int deleteMaterialIndexValue(String materialInfoId, String materialIndexId) throws BOSException, EASBizException;
}