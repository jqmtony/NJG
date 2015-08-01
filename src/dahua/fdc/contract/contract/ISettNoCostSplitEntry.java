package com.kingdee.eas.fdc.contract;

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
import com.kingdee.eas.fdc.basedata.IFDCNoCostSplitBillEntry;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ISettNoCostSplitEntry extends IFDCNoCostSplitBillEntry
{
    public SettNoCostSplitEntryInfo getSettNoCostSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SettNoCostSplitEntryInfo getSettNoCostSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SettNoCostSplitEntryInfo getSettNoCostSplitEntryInfo(String oql) throws BOSException, EASBizException;
    public SettNoCostSplitEntryCollection getSettNoCostSplitEntryCollection() throws BOSException;
    public SettNoCostSplitEntryCollection getSettNoCostSplitEntryCollection(EntityViewInfo view) throws BOSException;
    public SettNoCostSplitEntryCollection getSettNoCostSplitEntryCollection(String oql) throws BOSException;
}