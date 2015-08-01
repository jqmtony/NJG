package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynamicCostMonitorCAEntriesInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractDynamicCostMonitorCAEntriesInfo()
    {
        this("id");
    }
    protected AbstractDynamicCostMonitorCAEntriesInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 动态成本监控——成本科目分录 's 关联的动态成本监控 property 
     */
    public com.kingdee.eas.fdc.aimcost.DynamicCostMonitorInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.DynamicCostMonitorInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.DynamicCostMonitorInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 动态成本监控——成本科目分录 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:动态成本监控——成本科目分录's 待发生成本property 
     */
    public java.math.BigDecimal getEnxpectedToHappenAmt()
    {
        return getBigDecimal("enxpectedToHappenAmt");
    }
    public void setEnxpectedToHappenAmt(java.math.BigDecimal item)
    {
        setBigDecimal("enxpectedToHappenAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1B46874D");
    }
}