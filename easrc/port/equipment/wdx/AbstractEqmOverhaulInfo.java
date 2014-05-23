package com.kingdee.eas.port.equipment.wdx;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEqmOverhaulInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractEqmOverhaulInfo()
    {
        this("id");
    }
    protected AbstractEqmOverhaulInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.equipment.wdx.EqmOverhaulE1Collection());
    }
    /**
     * Object: 大修项目实施报告 's 实施单位 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getImplementUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("implementUnit");
    }
    public void setImplementUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("implementUnit", item);
    }
    /**
     * Object: 大修项目实施报告 's 项目编号 property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProjectNumber()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("projectNumber");
    }
    public void setProjectNumber(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("projectNumber", item);
    }
    /**
     * Object: 大修项目实施报告 's 项目名称 property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProjectName()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("projectName");
    }
    public void setProjectName(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("projectName", item);
    }
    /**
     * Object:大修项目实施报告's 决算费用property 
     */
    public java.math.BigDecimal getExpenseAccount()
    {
        return getBigDecimal("expenseAccount");
    }
    public void setExpenseAccount(java.math.BigDecimal item)
    {
        setBigDecimal("expenseAccount", item);
    }
    /**
     * Object:大修项目实施报告's 计划费用property 
     */
    public java.math.BigDecimal getPlanCost()
    {
        return getBigDecimal("planCost");
    }
    public void setPlanCost(java.math.BigDecimal item)
    {
        setBigDecimal("planCost", item);
    }
    /**
     * Object:大修项目实施报告's 预算费用property 
     */
    public java.math.BigDecimal getEstimateCost()
    {
        return getBigDecimal("estimateCost");
    }
    public void setEstimateCost(java.math.BigDecimal item)
    {
        setBigDecimal("estimateCost", item);
    }
    /**
     * Object: 大修项目实施报告 's 项目负责人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getProjectLeader()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("projectLeader");
    }
    public void setProjectLeader(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("projectLeader", item);
    }
    /**
     * Object:大修项目实施报告's 开工日期property 
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
     * Object:大修项目实施报告's 完工日期property 
     */
    public java.util.Date getCompleteDate()
    {
        return getDate("completeDate");
    }
    public void setCompleteDate(java.util.Date item)
    {
        setDate("completeDate", item);
    }
    /**
     * Object:大修项目实施报告's 修理方案property 
     */
    public String getRepairProgram()
    {
        return getString("RepairProgram");
    }
    public void setRepairProgram(String item)
    {
        setString("RepairProgram", item);
    }
    /**
     * Object: 大修项目实施报告 's 设备维大修 property 
     */
    public com.kingdee.eas.port.equipment.wdx.EqmOverhaulE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.wdx.EqmOverhaulE1Collection)get("E1");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("803AE46D");
    }
}