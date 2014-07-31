package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractYearInvestPlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractYearInvestPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractYearInvestPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 固定资产投资 's null property 
     */
    public com.kingdee.eas.port.pm.invest.YearInvestPlanInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.YearInvestPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.YearInvestPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:固定资产投资's 费用名称property 
     */
    public String getCostName()
    {
        return getString("costName");
    }
    public void setCostName(String item)
    {
        setString("costName", item);
    }
    /**
     * Object:固定资产投资's 概算金额property 
     */
    public java.math.BigDecimal getEstimate()
    {
        return getBigDecimal("estimate");
    }
    public void setEstimate(java.math.BigDecimal item)
    {
        setBigDecimal("estimate", item);
    }
    /**
     * Object:固定资产投资's 本年度投资预算property 
     */
    public java.math.BigDecimal getYearInvestBudget()
    {
        return getBigDecimal("yearInvestBudget");
    }
    public void setYearInvestBudget(java.math.BigDecimal item)
    {
        setBigDecimal("yearInvestBudget", item);
    }
    /**
     * Object:固定资产投资's 计划启动时间property 
     */
    public java.util.Date getPlanStartT()
    {
        return getDate("planStartT");
    }
    public void setPlanStartT(java.util.Date item)
    {
        setDate("planStartT", item);
    }
    /**
     * Object:固定资产投资's 计划验收时间property 
     */
    public java.util.Date getAcceptTime()
    {
        return getDate("acceptTime");
    }
    public void setAcceptTime(java.util.Date item)
    {
        setDate("acceptTime", item);
    }
    /**
     * Object:固定资产投资's 说明property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: 固定资产投资 's 费用类型 property 
     */
    public com.kingdee.eas.port.pm.base.CostTypeTreeInfo getCostType()
    {
        return (com.kingdee.eas.port.pm.base.CostTypeTreeInfo)get("costType");
    }
    public void setCostType(com.kingdee.eas.port.pm.base.CostTypeTreeInfo item)
    {
        put("costType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2960155A");
    }
}