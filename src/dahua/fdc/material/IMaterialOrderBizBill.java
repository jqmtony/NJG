package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IMaterialOrderBizBill extends IFDCBill
{
    public MaterialOrderBizBillCollection getMaterialOrderBizBillCollection() throws BOSException;
    public MaterialOrderBizBillCollection getMaterialOrderBizBillCollection(EntityViewInfo view) throws BOSException;
    public MaterialOrderBizBillCollection getMaterialOrderBizBillCollection(String oql) throws BOSException;
    public MaterialOrderBizBillInfo getMaterialOrderBizBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MaterialOrderBizBillInfo getMaterialOrderBizBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MaterialOrderBizBillInfo getMaterialOrderBizBillInfo(String oql) throws BOSException, EASBizException;
}