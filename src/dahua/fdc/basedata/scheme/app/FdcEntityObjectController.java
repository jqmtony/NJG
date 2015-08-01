package com.kingdee.eas.fdc.basedata.scheme.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectInfo;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.app.CoreBaseController;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FdcEntityObjectController extends CoreBaseController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FdcEntityObjectInfo getFdcEntityObjectInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FdcEntityObjectInfo getFdcEntityObjectInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FdcEntityObjectInfo getFdcEntityObjectInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, FdcEntityObjectInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, FdcEntityObjectInfo model) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, FdcEntityObjectInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, FdcEntityObjectInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, FdcEntityObjectInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public FdcEntityObjectCollection getFdcEntityObjectCollection(Context ctx) throws BOSException, RemoteException;
    public FdcEntityObjectCollection getFdcEntityObjectCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FdcEntityObjectCollection getFdcEntityObjectCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public FdcEntityObjectCollection synchronizeMD(Context ctx, String fullNamePerfix, boolean isPackage) throws BOSException, EASBizException, RemoteException;
    public FdcEntityObjectCollection synchronizeEasMD(Context ctx) throws BOSException, EASBizException, RemoteException;
    public FdcEntityObjectCollection synchronizeBaseMD(Context ctx) throws BOSException, EASBizException, RemoteException;
    public FdcEntityObjectCollection synchronizeFdcMD(Context ctx) throws BOSException, EASBizException, RemoteException;
}