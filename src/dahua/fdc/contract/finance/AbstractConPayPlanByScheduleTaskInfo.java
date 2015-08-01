package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConPayPlanByScheduleTaskInfo extends com.kingdee.eas.fdc.finance.PayPlanScheduleTaskBaseInfo implements Serializable 
{
    public AbstractConPayPlanByScheduleTaskInfo()
    {
        this("id");
    }
    protected AbstractConPayPlanByScheduleTaskInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 节点任务 's  property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanByScheduleInfo getParent11()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanByScheduleInfo)get("parent11");
    }
    public void setParent11(com.kingdee.eas.fdc.finance.ConPayPlanByScheduleInfo item)
    {
        put("parent11", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("50F198B1");
    }
}