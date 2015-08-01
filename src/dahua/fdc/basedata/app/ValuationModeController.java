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
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.ValuationModeCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.ValuationModeInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ValuationModeController extends DataBaseController
{
    public ValuationModeInfo getValuationModeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ValuationModeInfo getValuationModeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ValuationModeInfo getValuationModeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ValuationModeCollection getValuationModeCollection(Context ctx) throws BOSException, RemoteException;
    public ValuationModeCollection getValuationModeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ValuationModeCollection getValuationModeCollection(Context ctx, String oql) throws BOSException, RemoteException;
}