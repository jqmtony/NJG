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
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCBillWFFacadeController extends BizController
{
    public void setWFAuditNotPrint(Context ctx, BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException, RemoteException;
    public void setWFAuditOrgInfo(Context ctx, BOSUuid billId, BOSUuid auditorId, String org) throws BOSException, EASBizException, RemoteException;
    public List getWFAuditResultForPrint(Context ctx, String billId) throws BOSException, EASBizException, RemoteException;
    public Map getWFBillLastAuditorAndTime(Context ctx, Set billIds) throws BOSException, EASBizException, RemoteException;
}