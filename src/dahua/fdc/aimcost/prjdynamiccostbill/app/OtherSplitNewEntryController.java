package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app;

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
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitNewEntryCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.app.FDCSplitBillEntryController;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitNewEntryInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface OtherSplitNewEntryController extends FDCSplitBillEntryController
{
    public OtherSplitNewEntryInfo getOtherSplitNewEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public OtherSplitNewEntryInfo getOtherSplitNewEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public OtherSplitNewEntryInfo getOtherSplitNewEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public OtherSplitNewEntryCollection getOtherSplitNewEntryCollection(Context ctx) throws BOSException, RemoteException;
    public OtherSplitNewEntryCollection getOtherSplitNewEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public OtherSplitNewEntryCollection getOtherSplitNewEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}