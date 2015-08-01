package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.framework.IDataBase;

public interface IPayPlanNew extends IDataBase
{
    public PayPlanNewInfo getPayPlanNewInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PayPlanNewInfo getPayPlanNewInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PayPlanNewInfo getPayPlanNewInfo(String oql) throws BOSException, EASBizException;
    public PayPlanNewCollection getPayPlanNewCollection() throws BOSException;
    public PayPlanNewCollection getPayPlanNewCollection(EntityViewInfo view) throws BOSException;
    public PayPlanNewCollection getPayPlanNewCollection(String oql) throws BOSException;
    public PayPlanNewInfo caculate(PayPlanNewInfo info) throws BOSException, EASBizException;
    public void onScheduleChange(String scheduleId) throws BOSException, EASBizException;
    public void updateBySchedule(String payPlanNewId) throws BOSException, EASBizException;
    public void checkForCalculate(PayPlanNewInfo info) throws BOSException, EASBizException;
    public List getFDCSchTasksListByProjectID(String projectID) throws BOSException, EASBizException;
}