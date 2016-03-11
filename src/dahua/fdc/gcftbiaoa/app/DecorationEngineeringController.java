package com.kingdee.eas.fdc.gcftbiaoa.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringCollection;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.fdc.gcftbiaoa.DecorationEngineeringInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface DecorationEngineeringController extends CoreBillBaseController
{
    public DecorationEngineeringCollection getDecorationEngineeringCollection(Context ctx) throws BOSException, RemoteException;
    public DecorationEngineeringCollection getDecorationEngineeringCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public DecorationEngineeringCollection getDecorationEngineeringCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public DecorationEngineeringInfo getDecorationEngineeringInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public DecorationEngineeringInfo getDecorationEngineeringInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public DecorationEngineeringInfo getDecorationEngineeringInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void aduit(Context ctx, DecorationEngineeringInfo model) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, DecorationEngineeringInfo model) throws BOSException, EASBizException, RemoteException;
}