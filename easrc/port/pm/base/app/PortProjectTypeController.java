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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.app.TreeBaseController;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.port.pm.base.PortProjectTypeCollection;
import com.kingdee.eas.port.pm.base.PortProjectTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PortProjectTypeController extends TreeBaseController
{
    public PortProjectTypeInfo getPortProjectTypeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PortProjectTypeInfo getPortProjectTypeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PortProjectTypeInfo getPortProjectTypeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public PortProjectTypeCollection getPortProjectTypeCollection(Context ctx) throws BOSException, RemoteException;
    public PortProjectTypeCollection getPortProjectTypeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PortProjectTypeCollection getPortProjectTypeCollection(Context ctx, String oql) throws BOSException, RemoteException;
}