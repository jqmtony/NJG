package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.finance.ConPayPlanInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.finance.ConPayPlanCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ConPayPlanController extends FDCBillController
{
    public ConPayPlanInfo getConPayPlanInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ConPayPlanInfo getConPayPlanInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ConPayPlanInfo getConPayPlanInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ConPayPlanCollection getConPayPlanCollection(Context ctx) throws BOSException, RemoteException;
    public ConPayPlanCollection getConPayPlanCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ConPayPlanCollection getConPayPlanCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ConPayPlanInfo caculate(Context ctx, ConPayPlanInfo info) throws BOSException, EASBizException, RemoteException;
    public IObjectPK importPayPlan(Context ctx, String contractBillId, boolean isFromSch) throws BOSException, EASBizException, RemoteException;
    public void onScheduleChange(Context ctx, String scheduleId) throws BOSException, EASBizException, RemoteException;
    public void settleByMonth(Context ctx, ConPayPlanInfo info) throws BOSException, RemoteException;
    public void calPayPlanUnsign(Context ctx, int settleMonth, String programmingID) throws BOSException, EASBizException, RemoteException;
}