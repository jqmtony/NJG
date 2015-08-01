package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanNewUnsignInfo extends com.kingdee.eas.fdc.finance.PayPlanDataBaseInfo implements Serializable 
{
    public AbstractPayPlanNewUnsignInfo()
    {
        this("id");
    }
    protected AbstractPayPlanNewUnsignInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: δǩԼ��ϸ 's null property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanNewInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanNewInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.PayPlanNewInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("451C3FB4");
    }
}