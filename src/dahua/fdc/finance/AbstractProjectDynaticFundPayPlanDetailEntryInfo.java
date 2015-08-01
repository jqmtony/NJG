package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectDynaticFundPayPlanDetailEntryInfo extends com.kingdee.eas.fdc.finance.PayPlanDataBaseInfo implements Serializable 
{
    public AbstractProjectDynaticFundPayPlanDetailEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectDynaticFundPayPlanDetailEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目动态资金支付计划明细分录 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanEntryInfo getParent1()
    {
        return (com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanEntryInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanEntryInfo item)
    {
        put("parent1", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("04F78CB1");
    }
}