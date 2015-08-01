package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.REAutoRememberCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.CoreBaseController;
import com.kingdee.eas.fdc.basedata.REAutoRememberInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface REAutoRememberController extends CoreBaseController
{
    public REAutoRememberInfo getREAutoRememberInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public REAutoRememberInfo getREAutoRememberInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public REAutoRememberInfo getREAutoRememberInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public REAutoRememberCollection getREAutoRememberCollection(Context ctx) throws BOSException, RemoteException;
    public REAutoRememberCollection getREAutoRememberCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public REAutoRememberCollection getREAutoRememberCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void save(Context ctx, String userID, String orgUnitID, String function, String value) throws BOSException, RemoteException;
    public String getValue(Context ctx, String userID, String orgUnitID, String function) throws BOSException, RemoteException;
}