package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCNoCostSplitBillEntryController;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SettNoCostSplitEntryController extends FDCNoCostSplitBillEntryController
{
    public SettNoCostSplitEntryInfo getSettNoCostSplitEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SettNoCostSplitEntryInfo getSettNoCostSplitEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SettNoCostSplitEntryInfo getSettNoCostSplitEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SettNoCostSplitEntryCollection getSettNoCostSplitEntryCollection(Context ctx) throws BOSException, RemoteException;
    public SettNoCostSplitEntryCollection getSettNoCostSplitEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SettNoCostSplitEntryCollection getSettNoCostSplitEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}