package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanNewByScheduleTaskNameInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPayPlanNewByScheduleTaskNameInfo()
    {
        this("id");
    }
    protected AbstractPayPlanNewByScheduleTaskNameInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 节点名称 's  property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.PayPlanNewByScheduleInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:节点名称's 名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:节点名称's 编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("077C76BC");
    }
}