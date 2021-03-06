package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.FDCSplitBillCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCSplitBillController extends FDCBillController
{
    public FDCSplitBillInfo getFDCSplitBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCSplitBillInfo getFDCSplitBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCSplitBillInfo getFDCSplitBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCSplitBillCollection getFDCSplitBillCollection(Context ctx) throws BOSException, RemoteException;
    public FDCSplitBillCollection getFDCSplitBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCSplitBillCollection getFDCSplitBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public FDCSplitBillInfo getNewData(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public Map fetchInitParam(Context ctx) throws BOSException, EASBizException, RemoteException;
}