package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IOtherSplitBillEntry extends ICoreBillEntryBase
{
    public OtherSplitBillEntryInfo getOtherSplitBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public OtherSplitBillEntryInfo getOtherSplitBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public OtherSplitBillEntryInfo getOtherSplitBillEntryInfo(String oql) throws BOSException, EASBizException;
    public OtherSplitBillEntryCollection getOtherSplitBillEntryCollection() throws BOSException;
    public OtherSplitBillEntryCollection getOtherSplitBillEntryCollection(EntityViewInfo view) throws BOSException;
    public OtherSplitBillEntryCollection getOtherSplitBillEntryCollection(String oql) throws BOSException;
}