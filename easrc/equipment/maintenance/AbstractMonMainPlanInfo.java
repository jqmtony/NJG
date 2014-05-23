package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonMainPlanInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractMonMainPlanInfo()
    {
        this("id");
    }
    protected AbstractMonMainPlanInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.equipment.maintenance.MonMainPlanE1Collection());
    }
    /**
     * Object: 月度维保计划 's 计划月份 property 
     */
    public com.kingdee.eas.port.equipment.base.MonthTimeInfo getPlanMonth()
    {
        return (com.kingdee.eas.port.equipment.base.MonthTimeInfo)get("planMonth");
    }
    public void setPlanMonth(com.kingdee.eas.port.equipment.base.MonthTimeInfo item)
    {
        put("planMonth", item);
    }
    /**
     * Object:月度维保计划's 计划总费用property 
     */
    public java.math.BigDecimal getPlanTotalCost()
    {
        return getBigDecimal("planTotalCost");
    }
    public void setPlanTotalCost(java.math.BigDecimal item)
    {
        setBigDecimal("planTotalCost", item);
    }
    /**
     * Object: 月度维保计划 's 月度维保计划单 property 
     */
    public com.kingdee.eas.port.equipment.maintenance.MonMainPlanE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.maintenance.MonMainPlanE1Collection)get("E1");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A0CD335E");
    }
}