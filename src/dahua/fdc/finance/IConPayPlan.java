package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IConPayPlan extends IFDCBill
{
    public ConPayPlanInfo getConPayPlanInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ConPayPlanInfo getConPayPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ConPayPlanInfo getConPayPlanInfo(String oql) throws BOSException, EASBizException;
    public ConPayPlanCollection getConPayPlanCollection() throws BOSException;
    public ConPayPlanCollection getConPayPlanCollection(EntityViewInfo view) throws BOSException;
    public ConPayPlanCollection getConPayPlanCollection(String oql) throws BOSException;
    public ConPayPlanInfo caculate(ConPayPlanInfo info) throws BOSException, EASBizException;
    public IObjectPK importPayPlan(String contractBillId, boolean isFromSch) throws BOSException, EASBizException;
    public void onScheduleChange(String scheduleId) throws BOSException, EASBizException;
    public void settleByMonth(ConPayPlanInfo info) throws BOSException;
    public void calPayPlanUnsign(int settleMonth, String programmingID) throws BOSException, EASBizException;
}