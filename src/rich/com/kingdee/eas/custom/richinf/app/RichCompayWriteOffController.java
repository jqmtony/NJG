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
import com.kingdee.eas.custom.richinf.RichCompayWriteOffCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fi.ar.OtherBillCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.custom.richinf.RichCompayWriteOffInfo;
import com.kingdee.eas.custom.richinf.RichExamedCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RichCompayWriteOffController extends CoreBillBaseController
{
    public RichCompayWriteOffCollection getRichCompayWriteOffCollection(Context ctx) throws BOSException, RemoteException;
    public RichCompayWriteOffCollection getRichCompayWriteOffCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RichCompayWriteOffCollection getRichCompayWriteOffCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public RichCompayWriteOffInfo getRichCompayWriteOffInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RichCompayWriteOffInfo getRichCompayWriteOffInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RichCompayWriteOffInfo getRichCompayWriteOffInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public boolean aboutHxAndFanHx(Context ctx, OtherBillCollection fpColl, RichExamedCollection richExamColl, int hxType, RichCompayWriteOffInfo ov) throws BOSException, RemoteException;
}