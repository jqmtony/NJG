package com.kingdee.eas.port.pm.invest.investplan.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.port.pm.invest.investplan.InvestPlanDetailInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.port.pm.invest.investplan.InvestPlanDetailCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface InvestPlanDetailController extends CoreBillBaseController
{
    public InvestPlanDetailCollection getInvestPlanDetailCollection(Context ctx) throws BOSException, RemoteException;
    public InvestPlanDetailCollection getInvestPlanDetailCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public InvestPlanDetailCollection getInvestPlanDetailCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public InvestPlanDetailInfo getInvestPlanDetailInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public InvestPlanDetailInfo getInvestPlanDetailInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public InvestPlanDetailInfo getInvestPlanDetailInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
}