package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IMaterialEnterSum extends IFDCBill
{
    public MaterialEnterSumCollection getMaterialEnterSumCollection() throws BOSException;
    public MaterialEnterSumCollection getMaterialEnterSumCollection(EntityViewInfo view) throws BOSException;
    public MaterialEnterSumCollection getMaterialEnterSumCollection(String oql) throws BOSException;
    public MaterialEnterSumInfo getMaterialEnterSumInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MaterialEnterSumInfo getMaterialEnterSumInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MaterialEnterSumInfo getMaterialEnterSumInfo(String oql) throws BOSException, EASBizException;
    public boolean refSetPlanSumQty(List refList, FDCBillStateEnum state) throws BOSException;
}