package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanNewByScheduleDatazInfo extends com.kingdee.eas.fdc.finance.PayPlanDataBaseInfo implements Serializable 
{
    public AbstractPayPlanNewByScheduleDatazInfo()
    {
        this("id");
    }
    protected AbstractPayPlanNewByScheduleDatazInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 节点支付数据 's  property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo getParent11()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo)get("parent11");
    }
    public void setParent11(com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo item)
    {
        put("parent11", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FFED6484");
    }
}