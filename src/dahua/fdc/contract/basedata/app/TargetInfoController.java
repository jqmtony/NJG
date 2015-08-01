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
import com.kingdee.eas.fdc.basedata.TargetInfoCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.TargetInfoInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.CoreBaseController;
import com.kingdee.eas.fdc.basedata.TargetInfoEntryCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TargetInfoController extends CoreBaseController
{
    public TargetInfoInfo getTargetInfoInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TargetInfoInfo getTargetInfoInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TargetInfoInfo getTargetInfoInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TargetInfoCollection getTargetInfoCollection(Context ctx) throws BOSException, RemoteException;
    public TargetInfoCollection getTargetInfoCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TargetInfoCollection getTargetInfoCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public TargetInfoEntryCollection getInitedCollection(Context ctx, String prjId) throws BOSException, RemoteException;
}