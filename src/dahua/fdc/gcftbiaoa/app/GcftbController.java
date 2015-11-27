package com.kingdee.eas.fdc.gcftbiaoa.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.gcftbiaoa.GcftbCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface GcftbController extends CoreBillBaseController
{
    public GcftbCollection getGcftbCollection(Context ctx) throws BOSException, RemoteException;
    public GcftbCollection getGcftbCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public GcftbCollection getGcftbCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public GcftbInfo getGcftbInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public GcftbInfo getGcftbInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public GcftbInfo getGcftbInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, GcftbInfo model) throws BOSException, RemoteException;
    public void unaudit(Context ctx, GcftbInfo model) throws BOSException, RemoteException;
}