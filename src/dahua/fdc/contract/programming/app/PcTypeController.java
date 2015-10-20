package com.kingdee.eas.fdc.contract.programming.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.fdc.contract.programming.PcTypeCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.programming.PcTypeInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PcTypeController extends CoreBillBaseController
{
    public PcTypeCollection getPcTypeCollection(Context ctx) throws BOSException, RemoteException;
    public PcTypeCollection getPcTypeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PcTypeCollection getPcTypeCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public PcTypeInfo getPcTypeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PcTypeInfo getPcTypeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PcTypeInfo getPcTypeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
}