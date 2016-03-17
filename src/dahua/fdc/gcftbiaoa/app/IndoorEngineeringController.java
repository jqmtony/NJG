package com.kingdee.eas.fdc.gcftbiaoa.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.gcftbiaoa.IndoorEngineeringInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.gcftbiaoa.IndoorEngineeringCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface IndoorEngineeringController extends CoreBillBaseController
{
    public IndoorEngineeringCollection getIndoorEngineeringCollection(Context ctx) throws BOSException, RemoteException;
    public IndoorEngineeringCollection getIndoorEngineeringCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public IndoorEngineeringCollection getIndoorEngineeringCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IndoorEngineeringInfo getIndoorEngineeringInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IndoorEngineeringInfo getIndoorEngineeringInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public IndoorEngineeringInfo getIndoorEngineeringInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void aduit(Context ctx, IndoorEngineeringInfo model) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, IndoorEngineeringInfo model) throws BOSException, EASBizException, RemoteException;
}