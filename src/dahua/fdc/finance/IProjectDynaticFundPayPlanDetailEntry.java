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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IProjectDynaticFundPayPlanDetailEntry extends IPayPlanDataBase
{
    public ProjectDynaticFundPayPlanDetailEntryInfo getProjectDynaticFundPayPlanDetailEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProjectDynaticFundPayPlanDetailEntryInfo getProjectDynaticFundPayPlanDetailEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectDynaticFundPayPlanDetailEntryInfo getProjectDynaticFundPayPlanDetailEntryInfo(String oql) throws BOSException, EASBizException;
    public ProjectDynaticFundPayPlanDetailEntryCollection getProjectDynaticFundPayPlanDetailEntryCollection() throws BOSException;
    public ProjectDynaticFundPayPlanDetailEntryCollection getProjectDynaticFundPayPlanDetailEntryCollection(EntityViewInfo view) throws BOSException;
    public ProjectDynaticFundPayPlanDetailEntryCollection getProjectDynaticFundPayPlanDetailEntryCollection(String oql) throws BOSException;
}