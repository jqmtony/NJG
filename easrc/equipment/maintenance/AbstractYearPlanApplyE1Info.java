package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractYearPlanApplyE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractYearPlanApplyE1Info()
    {
        this("id");
    }
    protected AbstractYearPlanApplyE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��1������ 's null property 
     */
    public com.kingdee.eas.port.equipment.maintenance.YearPlanApplyInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.maintenance.YearPlanApplyInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.maintenance.YearPlanApplyInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��1������ 's �豸���� property 
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
     * Object:��1������'s �豸���property 
     */
    public String getEquNumber()
    {
        return getString("equNumber");
    }
    public void setEquNumber(String item)
    {
        setString("equNumber", item);
    }
    /**
     * Object:��1������'s ����ͺ�property 
     */
    public String getModel()
    {
        return getString("model");
    }
    public void setModel(String item)
    {
        setString("model", item);
    }
    /**
     * Object:��1������'s ʹ�ò���property 
     */
    public String getUseDepart()
    {
        return getString("useDepart");
    }
    public void setUseDepart(String item)
    {
        setString("useDepart", item);
    }
    /**
     * Object:��1������'s ά������property 
     */
    public String getMaintContent()
    {
        return getString("maintContent");
    }
    public void setMaintContent(String item)
    {
        setString("maintContent", item);
    }
    /**
     * Object: ��1������ 's ά������ property 
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
     * Object:��1������'s �ƻ���������property 
     */
    public java.math.BigDecimal getPlanXiuliDay()
    {
        return getBigDecimal("planXiuliDay");
    }
    public void setPlanXiuliDay(java.math.BigDecimal item)
    {
        setBigDecimal("planXiuliDay", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7F9EE844");
    }
}