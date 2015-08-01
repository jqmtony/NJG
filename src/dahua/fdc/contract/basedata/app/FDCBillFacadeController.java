package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.Object;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCBillFacadeController extends BizController
{
    public void autoChangeSettle(Context ctx, String settleId, boolean isAll) throws BOSException, EASBizException, RemoteException;
    public Object execAction(Context ctx, Map param) throws BOSException, RemoteException;
    public FDCSplitBillInfo autoPropSplit(Context ctx, String type, Map dataMap) throws BOSException, EASBizException, RemoteException;
    public void setBillAudited4wf(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setBillAudited4wf2(Context ctx, BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException, RemoteException;
    public void setBillAuditing4wf2(Context ctx, BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException, RemoteException;
    public void dealSaveAboutConChange(Context ctx, String settleId) throws BOSException, EASBizException, RemoteException;
}