package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRepairOrderInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractRepairOrderInfo()
    {
        this("id");
    }
    protected AbstractRepairOrderInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.equipment.maintenance.RepairOrderE1Collection());
    }
    /**
     * Object: 维修单 's 设备名称 property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getEquName()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("equName");
    }
    public void setEquName(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("equName", item);
    }
    /**
     * Object:维修单's 设备型号property 
     */
    public String getEquModel()
    {
        return getString("equModel");
    }
    public void setEquModel(String item)
    {
        setString("equModel", item);
    }
    /**
     * Object:维修单's 设备地点property 
     */
    public String getEquAddress()
    {
        return getString("equAddress");
    }
    public void setEquAddress(String item)
    {
        setString("equAddress", item);
    }
    /**
     * Object: 维修单 's 报修部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRepairDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("repairDepart");
    }
    public void setRepairDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("repairDepart", item);
    }
    /**
     * Object: 维修单 's 报修人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRepairPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("repairPerson");
    }
    public void setRepairPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("repairPerson", item);
    }
    /**
     * Object:维修单's 报修时间property 
     */
    public java.util.Date getRepairTime()
    {
        return getDate("repairTime");
    }
    public void setRepairTime(java.util.Date item)
    {
        setDate("repairTime", item);
    }
    /**
     * Object:维修单's 报修内容及要求property 
     */
    public String getRepairContent()
    {
        return getString("repairContent");
    }
    public void setRepairContent(String item)
    {
        setString("repairContent", item);
    }
    /**
     * Object: 维修单 's 受理人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getAssignee()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("assignee");
    }
    public void setAssignee(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("assignee", item);
    }
    /**
     * Object:维修单's 受理时间property 
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
     * Object: 维修单 's 维修类型 property 
     */
    public com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo getMaintenanceType()
    {
        return (com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo)get("maintenanceType");
    }
    public void setMaintenanceType(com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo item)
    {
        put("maintenanceType", item);
    }
    /**
     * Object:维修单's 维修方案property 
     */
    public String getMaintenanceProgram()
    {
        return getString("maintenanceProgram");
    }
    public void setMaintenanceProgram(String item)
    {
        setString("maintenanceProgram", item);
    }
    /**
     * Object: 维修单 's 维修明细 property 
     */
    public com.kingdee.eas.port.equipment.maintenance.RepairOrderE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.maintenance.RepairOrderE1Collection)get("E1");
    }
    /**
     * Object:维修单's 备注property 
     */
    public String getBIMUDF0021()
    {
        return getString("BIMUDF0021");
    }
    public void setBIMUDF0021(String item)
    {
        setString("BIMUDF0021", item);
    }
    /**
     * Object:维修单's 修复时间property 
     */
    public java.util.Date getRepairDate()
    {
        return getDate("repairDate");
    }
    public void setRepairDate(java.util.Date item)
    {
        setDate("repairDate", item);
    }
    /**
     * Object:维修单's 验收情况property 
     */
    public String getAcceptSituation()
    {
        return getString("acceptSituation");
    }
    public void setAcceptSituation(String item)
    {
        setString("acceptSituation", item);
    }
    /**
     * Object: 维修单 's 交付人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getDeliveryPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("deliveryPerson");
    }
    public void setDeliveryPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("deliveryPerson", item);
    }
    /**
     * Object: 维修单 's 接收人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRecipient()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("recipient");
    }
    public void setRecipient(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("recipient", item);
    }
    /**
     * Object:维修单's 交接时间property 
     */
    public java.util.Date getTransferTime()
    {
        return getDate("transferTime");
    }
    public void setTransferTime(java.util.Date item)
    {
        setDate("transferTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F96E9B71");
    }
}