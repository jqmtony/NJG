package com.kingdee.eas.fdc.costindexdb.database.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.costindexdb.database.BasePointTempInfo;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.costindexdb.database.BasePointTempCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BasePointTempController extends DataBaseController
{
    public BasePointTempInfo getBasePointTempInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BasePointTempInfo getBasePointTempInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BasePointTempInfo getBasePointTempInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BasePointTempCollection getBasePointTempCollection(Context ctx) throws BOSException, RemoteException;
    public BasePointTempCollection getBasePointTempCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BasePointTempCollection getBasePointTempCollection(Context ctx, String oql) throws BOSException, RemoteException;
}