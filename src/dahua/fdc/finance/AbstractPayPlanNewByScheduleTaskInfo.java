package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanNewByScheduleTaskInfo extends com.kingdee.eas.fdc.finance.PayPlanScheduleTaskBaseInfo implements Serializable 
{
    public AbstractPayPlanNewByScheduleTaskInfo()
    {
        this("id");
    }
    protected AbstractPayPlanNewByScheduleTaskInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 任务节点 's null property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C6383891");
    }
}