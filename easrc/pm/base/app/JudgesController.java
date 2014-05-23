package com.kingdee.eas.port.pm.base.app;

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
import com.kingdee.eas.port.pm.base.JudgesCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.port.pm.base.JudgesInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface JudgesController extends DataBaseController
{
    public JudgesInfo getJudgesInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public JudgesInfo getJudgesInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public JudgesInfo getJudgesInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public JudgesCollection getJudgesCollection(Context ctx) throws BOSException, RemoteException;
    public JudgesCollection getJudgesCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public JudgesCollection getJudgesCollection(Context ctx, String oql) throws BOSException, RemoteException;
}