package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.app.FDCSplitBillController;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ConChangeSplitController extends FDCSplitBillController
{
    public ConChangeSplitInfo getConChangeSplitInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ConChangeSplitInfo getConChangeSplitInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ConChangeSplitInfo getConChangeSplitInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ConChangeSplitCollection getConChangeSplitCollection(Context ctx) throws BOSException, RemoteException;
    public ConChangeSplitCollection getConChangeSplitCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ConChangeSplitCollection getConChangeSplitCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void autoSplit(Context ctx, IObjectPK changePK) throws BOSException, EASBizException, RemoteException;
    public void changeSettle(Context ctx, ContractChangeBillInfo changeInfo) throws BOSException, EASBizException, RemoteException;
    public void autoSplit4(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
}