package com.kingdee.eas.fdc.basedata;

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
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IFDCNoCostSplitBillEntry extends IBillEntryBase
{
    public FDCNoCostSplitBillEntryInfo getFDCNoCostSplitBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCNoCostSplitBillEntryInfo getFDCNoCostSplitBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCNoCostSplitBillEntryInfo getFDCNoCostSplitBillEntryInfo(String oql) throws BOSException, EASBizException;
    public FDCNoCostSplitBillEntryCollection getFDCNoCostSplitBillEntryCollection() throws BOSException;
    public FDCNoCostSplitBillEntryCollection getFDCNoCostSplitBillEntryCollection(EntityViewInfo view) throws BOSException;
    public FDCNoCostSplitBillEntryCollection getFDCNoCostSplitBillEntryCollection(String oql) throws BOSException;
}