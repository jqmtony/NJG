package com.kingdee.eas.port.equipment.base.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.port.equipment.base.EqmTypeCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.port.equipment.base.EqmTypeInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface EqmTypeController extends DataBaseController
{
    public EqmTypeInfo getEqmTypeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public EqmTypeInfo getEqmTypeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public EqmTypeInfo getEqmTypeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public EqmTypeCollection getEqmTypeCollection(Context ctx) throws BOSException, RemoteException;
    public EqmTypeCollection getEqmTypeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public EqmTypeCollection getEqmTypeCollection(Context ctx, String oql) throws BOSException, RemoteException;
}