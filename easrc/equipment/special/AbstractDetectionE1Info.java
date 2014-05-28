package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDetectionE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractDetectionE1Info()
    {
        this("id");
    }
    protected AbstractDetectionE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第1个表体 's null property 
     */
    public com.kingdee.eas.port.equipment.special.DetectionInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.special.DetectionInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.special.DetectionInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 第1个表体 's 设备类型 property 
     */
    public com.kingdee.eas.port.equipment.base.EqmTypeInfo getDeviceType()
    {
        return (com.kingdee.eas.port.equipment.base.EqmTypeInfo)get("deviceType");
    }
    public void setDeviceType(com.kingdee.eas.port.equipment.base.EqmTypeInfo item)
    {
        put("deviceType", item);
    }
    /**
     * Object:第1个表体's 计划数property 
     */
    public java.math.BigDecimal getPlanNumber1()
    {
        return getBigDecimal("planNumber1");
    }
    public void setPlanNumber1(java.math.BigDecimal item)
    {
        setBigDecimal("planNumber1", item);
    }
    /**
     * Object:第1个表体's 实际数property 
     */
    public java.math.BigDecimal getActualNumber1()
    {
        return getBigDecimal("actualNumber1");
    }
    public void setActualNumber1(java.math.BigDecimal item)
    {
        setBigDecimal("actualNumber1", item);
    }
    /**
     * Object:第1个表体's 合格数property 
     */
    public java.math.BigDecimal getQualifiedNumber1()
    {
        return getBigDecimal("qualifiedNumber1");
    }
    public void setQualifiedNumber1(java.math.BigDecimal item)
    {
        setBigDecimal("qualifiedNumber1", item);
    }
    /**
     * Object:第1个表体's 合格率property 
     */
    public java.math.BigDecimal getQualifiedRate1()
    {
        return getBigDecimal("qualifiedRate1");
    }
    public void setQualifiedRate1(java.math.BigDecimal item)
    {
        setBigDecimal("qualifiedRate1", item);
    }
    /**
     * Object:第1个表体's 计划数property 
     */
    public java.math.BigDecimal getPlanNumber2()
    {
        return getBigDecimal("planNumber2");
    }
    public void setPlanNumber2(java.math.BigDecimal item)
    {
        setBigDecimal("planNumber2", item);
    }
    /**
     * Object:第1个表体's 实际数property 
     */
    public java.math.BigDecimal getActualNumber2()
    {
        return getBigDecimal("actualNumber2");
    }
    public void setActualNumber2(java.math.BigDecimal item)
    {
        setBigDecimal("actualNumber2", item);
    }
    /**
     * Object:第1个表体's 合格数property 
     */
    public java.math.BigDecimal getQualifiedNumber2()
    {
        return getBigDecimal("qualifiedNumber2");
    }
    public void setQualifiedNumber2(java.math.BigDecimal item)
    {
        setBigDecimal("qualifiedNumber2", item);
    }
    /**
     * Object:第1个表体's 合格率property 
     */
    public java.math.BigDecimal getQualifiedRate2()
    {
        return getBigDecimal("qualifiedRate2");
    }
    public void setQualifiedRate2(java.math.BigDecimal item)
    {
        setBigDecimal("qualifiedRate2", item);
    }
    /**
     * Object:第1个表体's 设备类型property 
     */
    public String getDeviceType1()
    {
        return getString("deviceType1");
    }
    public void setDeviceType1(String item)
    {
        setString("deviceType1", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3B0E3727");
    }
}