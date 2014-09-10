package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonMainPlanE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMonMainPlanE1Info()
    {
        this("id");
    }
    protected AbstractMonMainPlanE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 月度维保计划单 's null property 
     */
    public com.kingdee.eas.port.equipment.maintenance.MonMainPlanInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.maintenance.MonMainPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.maintenance.MonMainPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 月度维保计划单 's 设备编号 property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getEquNumber()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("equNumber");
    }
    public void setEquNumber(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("equNumber", item);
    }
    /**
     * Object:月度维保计划单's 设备名称property 
     */
    public String getEquName()
    {
        return getString("equName");
    }
    public void setEquName(String item)
    {
        setString("equName", item);
    }
    /**
     * Object: 月度维保计划单 's 申请部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getAppDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("appDepart");
    }
    public void setAppDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("appDepart", item);
    }
    /**
     * Object:月度维保计划单's 维保内容property 
     */
    public String getMainContent()
    {
        return getString("mainContent");
    }
    public void setMainContent(String item)
    {
        setString("mainContent", item);
    }
    /**
     * Object:月度维保计划单's 计划开工时间property 
     */
    public java.util.Date getPlanStartTime()
    {
        return getDate("planStartTime");
    }
    public void setPlanStartTime(java.util.Date item)
    {
        setDate("planStartTime", item);
    }
    /**
     * Object:月度维保计划单's 计划完工时间property 
     */
    public java.util.Date getPlanCompleteT()
    {
        return getDate("planCompleteT");
    }
    public void setPlanCompleteT(java.util.Date item)
    {
        setDate("planCompleteT", item);
    }
    /**
     * Object:月度维保计划单's 计划费用property 
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
     * Object: 月度维保计划单 's 实施单位 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getImplementDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("implementDepart");
    }
    public void setImplementDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("implementDepart", item);
    }
    /**
     * Object:月度维保计划单's 实际完成时间property 
     */
    public java.util.Date getActualCompleteT()
    {
        return getDate("actualCompleteT");
    }
    public void setActualCompleteT(java.util.Date item)
    {
        setDate("actualCompleteT", item);
    }
    /**
     * Object:月度维保计划单's 完成情况property 
     */
    public String getComplete()
    {
        return getString("complete");
    }
    public void setComplete(String item)
    {
        setString("complete", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A24DDC6A");
    }
}