package com.kingdee.eas.port.pm.invest.investplan;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillBase;

public interface IInvestPlanDetail extends ICoreBillBase
{
    public InvestPlanDetailCollection getInvestPlanDetailCollection() throws BOSException;
    public InvestPlanDetailCollection getInvestPlanDetailCollection(EntityViewInfo view) throws BOSException;
    public InvestPlanDetailCollection getInvestPlanDetailCollection(String oql) throws BOSException;
    public InvestPlanDetailInfo getInvestPlanDetailInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InvestPlanDetailInfo getInvestPlanDetailInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InvestPlanDetailInfo getInvestPlanDetailInfo(String oql) throws BOSException, EASBizException;
}