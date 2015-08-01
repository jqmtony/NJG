package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.dao.IObjectPK;
import java.util.List;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCBillController extends CoreBillBaseController
{
    public FDCBillInfo getFDCBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCBillInfo getFDCBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCBillInfo getFDCBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCBillCollection getFDCBillCollection(Context ctx) throws BOSException, RemoteException;
    public FDCBillCollection getFDCBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCBillCollection getFDCBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void audit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void cancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void cancelCancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void cancel(Context ctx, IObjectPK[] pkArray) throws BOSException, EASBizException, RemoteException;
    public void cancelCancel(Context ctx, IObjectPK[] pkArray) throws BOSException, EASBizException, RemoteException;
    public boolean checkCanSubmit(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public Map fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
    public Map fetchFilterInitData(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
    public void setRespite(Context ctx, BOSUuid billId, boolean value) throws BOSException, EASBizException, RemoteException;
    public void setRespite(Context ctx, List ids, boolean value) throws BOSException, EASBizException, RemoteException;
}