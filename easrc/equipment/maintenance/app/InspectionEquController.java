package com.kingdee.eas.port.equipment.maintenance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.xr.app.XRBillBaseController;
import com.kingdee.eas.port.equipment.maintenance.InspectionEquInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.port.equipment.maintenance.InspectionEquCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface InspectionEquController extends XRBillBaseController
{
    public void addnew(Context ctx, IObjectPK pk, InspectionEquInfo model) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, InspectionEquInfo model) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public InspectionEquCollection getInspectionEquCollection(Context ctx) throws BOSException, RemoteException;
    public InspectionEquCollection getInspectionEquCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public InspectionEquCollection getInspectionEquCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public InspectionEquInfo getInspectionEquInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public InspectionEquInfo getInspectionEquInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public InspectionEquInfo getInspectionEquInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, InspectionEquInfo model) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, InspectionEquInfo model) throws BOSException, RemoteException;
    public void updatePartial(Context ctx, InspectionEquInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public String getBindingProperty(Context ctx) throws BOSException, RemoteException;
    public void excel(Context ctx, InspectionEquInfo model) throws BOSException, RemoteException;
}