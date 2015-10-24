package com.kingdee.eas.fdc.photomanager.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.photomanager.PhotoAuditInfo;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.photomanager.PhotoAuditCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PhotoAuditController extends CoreBillBaseController
{
    public PhotoAuditCollection getPhotoAuditCollection(Context ctx) throws BOSException, RemoteException;
    public PhotoAuditCollection getPhotoAuditCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PhotoAuditCollection getPhotoAuditCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public PhotoAuditInfo getPhotoAuditInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PhotoAuditInfo getPhotoAuditInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PhotoAuditInfo getPhotoAuditInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, PhotoAuditInfo model) throws BOSException, RemoteException;
    public void unAudit(Context ctx, PhotoAuditInfo model) throws BOSException, RemoteException;
}