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
import com.kingdee.eas.fdc.gcftbiaoa.SwbCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.gcftbiaoa.SwbInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SwbController extends CoreBillBaseController
{
    public SwbCollection getSwbCollection(Context ctx) throws BOSException, RemoteException;
    public SwbCollection getSwbCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SwbCollection getSwbCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public SwbInfo getSwbInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SwbInfo getSwbInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SwbInfo getSwbInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void aduit(Context ctx, SwbInfo model) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, SwbInfo model) throws BOSException, EASBizException, RemoteException;
}