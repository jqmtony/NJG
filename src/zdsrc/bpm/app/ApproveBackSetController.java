package com.kingdee.eas.bpm.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.bpm.ApproveBackSetCollection;
import com.kingdee.eas.bpm.ApproveBackSetInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ApproveBackSetController extends DataBaseController
{
    public ApproveBackSetInfo getApproveBackSetInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ApproveBackSetInfo getApproveBackSetInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ApproveBackSetInfo getApproveBackSetInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ApproveBackSetCollection getApproveBackSetCollection(Context ctx) throws BOSException, RemoteException;
    public ApproveBackSetCollection getApproveBackSetCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ApproveBackSetCollection getApproveBackSetCollection(Context ctx, String oql) throws BOSException, RemoteException;
}