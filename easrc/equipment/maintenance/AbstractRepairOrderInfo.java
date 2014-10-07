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
     * Object: ά�޵� 's �豸���� property 
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
     * Object:ά�޵�'s �豸�ͺ�property 
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
     * Object:ά�޵�'s �豸�ص�property 
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
     * Object: ά�޵� 's ���޲��� property 
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
     * Object: ά�޵� 's ������ property 
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
     * Object:ά�޵�'s ����ʱ��property 
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
     * Object:ά�޵�'s �������ݼ�Ҫ��property 
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
     * Object: ά�޵� 's ������ property 
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
     * Object:ά�޵�'s ����ʱ��property 
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
     * Object: ά�޵� 's ά������ property 
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
     * Object:ά�޵�'s ά�޷���property 
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
     * Object: ά�޵� 's ά����ϸ property 
     */
    public com.kingdee.eas.port.equipment.maintenance.RepairOrderE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.maintenance.RepairOrderE1Collection)get("E1");
    }
    /**
     * Object:ά�޵�'s ��עproperty 
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
     * Object:ά�޵�'s �޸�ʱ��property 
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
     * Object:ά�޵�'s �������property 
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
     * Object: ά�޵� 's ������ property 
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
     * Object: ά�޵� 's ������ property 
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
     * Object:ά�޵�'s ����ʱ��property 
     */
    public java.util.Date getTransferTime()
    {
        return getDate("transferTime");
    }
    public void setTransferTime(java.util.Date item)
    {
        setDate("transferTime", item);
    }
    /**
     * Object: ά�޵� 's ������ property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getSlDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("slDepart");
    }
    public void setSlDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("slDepart", item);
    }
    /**
     * Object:ά�޵�'s ����property 
     */
    public boolean isSelfStudy()
    {
        return getBoolean("selfStudy");
    }
    public void setSelfStudy(boolean item)
    {
        setBoolean("selfStudy", item);
    }
    /**
     * Object:ά�޵�'s ί������property 
     */
    public boolean isOutsourcing()
    {
        return getBoolean("outsourcing");
    }
    public void setOutsourcing(boolean item)
    {
        setBoolean("outsourcing", item);
    }
    /**
     * Object:ά�޵�'s ���޷���property 
     */
    public java.math.BigDecimal getSelfAmount()
    {
        return getBigDecimal("selfAmount");
    }
    public void setSelfAmount(java.math.BigDecimal item)
    {
        setBigDecimal("selfAmount", item);
    }
    /**
     * Object:ά�޵�'s ί���������property 
     */
    public java.math.BigDecimal getOutAmount()
    {
        return getBigDecimal("outAmount");
    }
    public void setOutAmount(java.math.BigDecimal item)
    {
        setBigDecimal("outAmount", item);
    }
    /**
     * Object:ά�޵�'s �Ƿ�ƻ���property 
     */
    public boolean isSfjhn()
    {
        return getBoolean("sfjhn");
    }
    public void setSfjhn(boolean item)
    {
        setBoolean("sfjhn", item);
    }
    /**
     * Object: ά�޵� 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getBaoxiuren()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("baoxiuren");
    }
    public void setBaoxiuren(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("baoxiuren", item);
    }
    /**
     * Object:ά�޵�'s ����ʱ��property 
     */
    public java.util.Date getBaoxiuTime()
    {
        return getDate("baoxiuTime");
    }
    public void setBaoxiuTime(java.util.Date item)
    {
        setDate("baoxiuTime", item);
    }
    /**
     * Object:ά�޵�'s ʵ�ʿ�������property 
     */
    public java.util.Date getActualStartTime()
    {
        return getDate("actualStartTime");
    }
    public void setActualStartTime(java.util.Date item)
    {
        setDate("actualStartTime", item);
    }
    /**
     * Object:ά�޵�'s ʵ���깤����property 
     */
    public java.util.Date getActualEndTime()
    {
        return getDate("actualEndTime");
    }
    public void setActualEndTime(java.util.Date item)
    {
        setDate("actualEndTime", item);
    }
    /**
     * Object:ά�޵�'s �ƻ���������property 
     */
    public java.util.Date getPlanStartTime()
    {
        return getDate("planStartTime");
    }
    public void setPlanStartTime(java.util.Date item)
    {
        setDate("planStartTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F96E9B71");
    }
}