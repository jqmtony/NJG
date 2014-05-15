package com.kingdee.eas.port.equipment.base.app;

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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.port.equipment.base.SpecialCheckItemCollection;
import com.kingdee.eas.port.equipment.base.SpecialCheckItemInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SpecialCheckItemController extends TreeBaseController
{
    public SpecialCheckItemInfo getSpecialCheckItemInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SpecialCheckItemInfo getSpecialCheckItemInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SpecialCheckItemInfo getSpecialCheckItemInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SpecialCheckItemCollection getSpecialCheckItemCollection(Context ctx) throws BOSException, RemoteException;
    public SpecialCheckItemCollection getSpecialCheckItemCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SpecialCheckItemCollection getSpecialCheckItemCollection(Context ctx, String oql) throws BOSException, RemoteException;
}