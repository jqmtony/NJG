package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.contract.PcontractTrackBillCollection;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.PcontractTrackBillInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PcontractTrackBillController extends CoreBillBaseController
{
    public PcontractTrackBillCollection getPcontractTrackBillCollection(Context ctx) throws BOSException, RemoteException;
    public PcontractTrackBillCollection getPcontractTrackBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PcontractTrackBillCollection getPcontractTrackBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public PcontractTrackBillInfo getPcontractTrackBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PcontractTrackBillInfo getPcontractTrackBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PcontractTrackBillInfo getPcontractTrackBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, PcontractTrackBillInfo model) throws BOSException, RemoteException;
    public void unaudit(Context ctx, PcontractTrackBillInfo model) throws BOSException, RemoteException;
    public void fix(Context ctx, PcontractTrackBillInfo model) throws BOSException, RemoteException;
}