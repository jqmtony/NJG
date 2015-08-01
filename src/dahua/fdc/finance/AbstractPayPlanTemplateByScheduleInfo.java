package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanTemplateByScheduleInfo extends com.kingdee.eas.fdc.finance.PayPlanByScheduleBaseInfo implements Serializable 
{
    public AbstractPayPlanTemplateByScheduleInfo()
    {
        this("id");
    }
    protected AbstractPayPlanTemplateByScheduleInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 按节点支付分录 's null property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanTemplateInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.PayPlanTemplateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:按节点支付分录's 备注property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6A9BABCA");
    }
}