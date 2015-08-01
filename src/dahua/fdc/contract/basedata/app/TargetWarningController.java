package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.TargetWarningCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.TargetWarningInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TargetWarningController extends FDCDataBaseController
{
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TargetWarningInfo getTargetWarningInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TargetWarningCollection getTargetWarningCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TargetWarningInfo getTargetWarningInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TargetWarningInfo getTargetWarningInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, TargetWarningInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, TargetWarningInfo model) throws BOSException, EASBizException, RemoteException;
    public TargetWarningCollection getTargetWarningCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean enable(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean disable(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
}