package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonMPApplicationE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMonMPApplicationE1Info()
    {
        this("id");
    }
    protected AbstractMonMPApplicationE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 月度维保计划申请 's null property 
     */
    public com.kingdee.eas.port.equipment.maintenance.MonMPApplicationInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.maintenance.MonMPApplicationInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.maintenance.MonMPApplicationInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 月度维保计划申请 's 设备编号 property 
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
     * Object:月度维保计划申请's 设备名称property 
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
     * Object:月度维保计划申请's 维保内容property 
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
     * Object:月度维保计划申请's 计划开工时间property 
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
     * Object:月度维保计划申请's 计划完工时间property 
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
     * Object:月度维保计划申请's 计划费用property 
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
     * Object:月度维保计划申请's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:月度维保计划申请's 实施单位property 
     */
    public String getImplemUnit()
    {
        return getString("implemUnit");
    }
    public void setImplemUnit(String item)
    {
        setString("implemUnit", item);
    }
    /**
     * Object: 月度维保计划申请 's 使用部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseDepat()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useDepat");
    }
    public void setUseDepat(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useDepat", item);
    }
    /**
     * Object: 月度维保计划申请 's 维修类型 property 
     */
    public com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo getWeixiuType()
    {
        return (com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo)get("weixiuType");
    }
    public void setWeixiuType(com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo item)
    {
        put("weixiuType", item);
    }
    /**
     * Object:月度维保计划申请's 计划修理天数property 
     */
    public java.math.BigDecimal getPlanWeixiuDay()
    {
        return getBigDecimal("planWeixiuDay");
    }
    public void setPlanWeixiuDay(java.math.BigDecimal item)
    {
        setBigDecimal("planWeixiuDay", item);
    }
    /**
     * Object:月度维保计划申请's 厂内编号property 
     */
    public String getCnNumber()
    {
        return getString("cnNumber");
    }
    public void setCnNumber(String item)
    {
        setString("cnNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B99906BD");
    }
}