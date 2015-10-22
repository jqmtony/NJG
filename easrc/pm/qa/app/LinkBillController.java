package com.kingdee.eas.port.pm.qa.app;

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
import com.kingdee.eas.port.pm.qa.LinkBillInfo;
import com.kingdee.eas.port.pm.qa.LinkBillCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface LinkBillController extends DataBaseController
{
    public LinkBillInfo getLinkBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public LinkBillInfo getLinkBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public LinkBillInfo getLinkBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public LinkBillCollection getLinkBillCollection(Context ctx) throws BOSException, RemoteException;
    public LinkBillCollection getLinkBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public LinkBillCollection getLinkBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
}