package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectBudgetE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectBudgetE1Info()
    {
        this("id");
    }
    protected AbstractProjectBudgetE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目预算 's null property 
     */
    public com.kingdee.eas.port.pm.invest.ProjectBudgetInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.ProjectBudgetInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.ProjectBudgetInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:项目预算's 预算项目property 
     */
    public String getBudgetName()
    {
        return getString("budgetName");
    }
    public void setBudgetName(String item)
    {
        setString("budgetName", item);
    }
    /**
     * Object:项目预算's 预算金额property 
     */
    public java.math.BigDecimal getBudgetAmount()
    {
        return getBigDecimal("budgetAmount");
    }
    public void setBudgetAmount(java.math.BigDecimal item)
    {
        setBigDecimal("budgetAmount", item);
    }
    /**
     * Object:项目预算's 计划启动时间property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:项目预算's 计划招标时间property 
     */
    public java.util.Date getInviteDate()
    {
        return getDate("inviteDate");
    }
    public void setInviteDate(java.util.Date item)
    {
        setDate("inviteDate", item);
    }
    /**
     * Object:项目预算's 计划开工时间property 
     */
    public java.util.Date getBuildDate()
    {
        return getDate("buildDate");
    }
    public void setBuildDate(java.util.Date item)
    {
        setDate("buildDate", item);
    }
    /**
     * Object:项目预算's 完工日期property 
     */
    public java.util.Date getFinishDate()
    {
        return getDate("finishDate");
    }
    public void setFinishDate(java.util.Date item)
    {
        setDate("finishDate", item);
    }
    /**
     * Object:项目预算's 竣工验收时间property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:项目预算's 是否单项工程property 
     */
    public boolean isUnitProject()
    {
        return getBoolean("unitProject");
    }
    public void setUnitProject(boolean item)
    {
        setBoolean("unitProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("932B3F4D");
    }
}