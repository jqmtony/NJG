package com.kingdee.eas.custom.richinf.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.custom.richinf.RichCustomWriteOffInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.custom.richinf.RichCustomWriteOffCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RichCustomWriteOffController extends CoreBillBaseController
{
    public RichCustomWriteOffCollection getRichCustomWriteOffCollection(Context ctx) throws BOSException, RemoteException;
    public RichCustomWriteOffCollection getRichCustomWriteOffCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RichCustomWriteOffCollection getRichCustomWriteOffCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public RichCustomWriteOffInfo getRichCustomWriteOffInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RichCustomWriteOffInfo getRichCustomWriteOffInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RichCustomWriteOffInfo getRichCustomWriteOffInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
}