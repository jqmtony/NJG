package com.kingdee.eas.fdc.costdb;

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
import com.kingdee.eas.framework.ICoreBase;

public interface IDBDynCostSnapShotSettEntry extends ICoreBase
{
    public DBDynCostSnapShotSettEntryInfo getDBDynCostSnapShotSettEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DBDynCostSnapShotSettEntryInfo getDBDynCostSnapShotSettEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DBDynCostSnapShotSettEntryInfo getDBDynCostSnapShotSettEntryInfo(String oql) throws BOSException, EASBizException;
    public DBDynCostSnapShotSettEntryCollection getDBDynCostSnapShotSettEntryCollection() throws BOSException;
    public DBDynCostSnapShotSettEntryCollection getDBDynCostSnapShotSettEntryCollection(EntityViewInfo view) throws BOSException;
    public DBDynCostSnapShotSettEntryCollection getDBDynCostSnapShotSettEntryCollection(String oql) throws BOSException;
}