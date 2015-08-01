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
import com.kingdee.eas.fdc.basedata.NoticeInfoCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.NoticeInfoInfo;
import com.kingdee.eas.framework.app.CoreBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface NoticeInfoController extends CoreBaseController
{
    public NoticeInfoInfo getNoticeInfoInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public NoticeInfoInfo getNoticeInfoInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public NoticeInfoInfo getNoticeInfoInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public NoticeInfoCollection getNoticeInfoCollection(Context ctx) throws BOSException, RemoteException;
    public NoticeInfoCollection getNoticeInfoCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public NoticeInfoCollection getNoticeInfoCollection(Context ctx, String oql) throws BOSException, RemoteException;
}