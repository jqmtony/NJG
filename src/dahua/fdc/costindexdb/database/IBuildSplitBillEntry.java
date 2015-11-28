package com.kingdee.eas.fdc.costindexdb.database;

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

public interface IBuildSplitBillEntry extends ICoreBillEntryBase
{
    public BuildSplitBillEntryInfo getBuildSplitBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BuildSplitBillEntryInfo getBuildSplitBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BuildSplitBillEntryInfo getBuildSplitBillEntryInfo(String oql) throws BOSException, EASBizException;
    public BuildSplitBillEntryCollection getBuildSplitBillEntryCollection() throws BOSException;
    public BuildSplitBillEntryCollection getBuildSplitBillEntryCollection(EntityViewInfo view) throws BOSException;
    public BuildSplitBillEntryCollection getBuildSplitBillEntryCollection(String oql) throws BOSException;
}