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
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.eas.custom.richinf.RichExamCheckTabInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.custom.richinf.RichExamCheckTabCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RichExamCheckTabController extends DataBaseController
{
    public RichExamCheckTabInfo getRichExamCheckTabInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RichExamCheckTabInfo getRichExamCheckTabInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RichExamCheckTabInfo getRichExamCheckTabInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RichExamCheckTabCollection getRichExamCheckTabCollection(Context ctx) throws BOSException, RemoteException;
    public RichExamCheckTabCollection getRichExamCheckTabCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RichExamCheckTabCollection getRichExamCheckTabCollection(Context ctx, String oql) throws BOSException, RemoteException;
}