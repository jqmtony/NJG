package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanTemplateByMonthInfo extends com.kingdee.eas.fdc.finance.PayPlanByMonthBaseInfo implements Serializable 
{
    public AbstractPayPlanTemplateByMonthInfo()
    {
        this("id");
    }
    protected AbstractPayPlanTemplateByMonthInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 按月支付分录 's null property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanTemplateInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.PayPlanTemplateInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("67E9CE2D");
    }
}