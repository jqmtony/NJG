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
import com.kingdee.eas.fdc.gcftbiaoa.OutdoorEngineeringInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.gcftbiaoa.OutdoorEngineeringCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface OutdoorEngineeringController extends CoreBillBaseController
{
    public OutdoorEngineeringCollection getOutdoorEngineeringCollection(Context ctx) throws BOSException, RemoteException;
    public OutdoorEngineeringCollection getOutdoorEngineeringCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public OutdoorEngineeringCollection getOutdoorEngineeringCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public OutdoorEngineeringInfo getOutdoorEngineeringInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public OutdoorEngineeringInfo getOutdoorEngineeringInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public OutdoorEngineeringInfo getOutdoorEngineeringInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void aduit(Context ctx, OutdoorEngineeringInfo model) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, OutdoorEngineeringInfo model) throws BOSException, EASBizException, RemoteException;
}