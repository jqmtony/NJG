package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillEntryCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.BillEntryBaseController;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillEntryInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface WorkLoadConfirmBillEntryController extends BillEntryBaseController
{
    public WorkLoadConfirmBillEntryInfo getWorkLoadConfirmBillEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public WorkLoadConfirmBillEntryInfo getWorkLoadConfirmBillEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public WorkLoadConfirmBillEntryInfo getWorkLoadConfirmBillEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public WorkLoadConfirmBillEntryCollection getWorkLoadConfirmBillEntryCollection(Context ctx) throws BOSException, RemoteException;
    public WorkLoadConfirmBillEntryCollection getWorkLoadConfirmBillEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public WorkLoadConfirmBillEntryCollection getWorkLoadConfirmBillEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}