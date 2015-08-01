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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import java.util.Set;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IProjectDynaticFundPayPlan extends IFDCBill
{
    public ProjectDynaticFundPayPlanInfo getProjectDynaticFundPayPlanInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProjectDynaticFundPayPlanInfo getProjectDynaticFundPayPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectDynaticFundPayPlanInfo getProjectDynaticFundPayPlanInfo(String oql) throws BOSException, EASBizException;
    public ProjectDynaticFundPayPlanCollection getProjectDynaticFundPayPlanCollection() throws BOSException;
    public ProjectDynaticFundPayPlanCollection getProjectDynaticFundPayPlanCollection(EntityViewInfo view) throws BOSException;
    public ProjectDynaticFundPayPlanCollection getProjectDynaticFundPayPlanCollection(String oql) throws BOSException;
    public void synData(Set curProjectIds) throws BOSException, EASBizException;
}