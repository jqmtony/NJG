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
import com.kingdee.eas.framework.ICoreBillBase;

public interface IBuildSplitBill extends ICoreBillBase
{
    public BuildSplitBillCollection getBuildSplitBillCollection() throws BOSException;
    public BuildSplitBillCollection getBuildSplitBillCollection(EntityViewInfo view) throws BOSException;
    public BuildSplitBillCollection getBuildSplitBillCollection(String oql) throws BOSException;
    public BuildSplitBillInfo getBuildSplitBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BuildSplitBillInfo getBuildSplitBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BuildSplitBillInfo getBuildSplitBillInfo(String oql) throws BOSException, EASBizException;
}