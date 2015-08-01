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
import com.kingdee.eas.framework.IObjectBase;

public interface IDBDynCostSnapShot extends IObjectBase
{
    public DBDynCostSnapShotCollection getDBDynCostSnapShotCollection() throws BOSException;
    public DBDynCostSnapShotCollection getDBDynCostSnapShotCollection(EntityViewInfo view) throws BOSException;
    public DBDynCostSnapShotCollection getDBDynCostSnapShotCollection(String oql) throws BOSException;
    public DBDynCostSnapShotInfo getDBDynCostSnapShotInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DBDynCostSnapShotInfo getDBDynCostSnapShotInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DBDynCostSnapShotInfo getDBDynCostSnapShotInfo(String oql) throws BOSException, EASBizException;
}