package com.kingdee.eas.port.equipment.insurance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInsuranceDeclarationStateE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInsuranceDeclarationStateE1Info()
    {
        this("id");
    }
    protected AbstractInsuranceDeclarationStateE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 保险申报明细 's null property 
     */
    public com.kingdee.eas.port.equipment.insurance.InsuranceDeclarationStateInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.insurance.InsuranceDeclarationStateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.insurance.InsuranceDeclarationStateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 保险申报明细 's 设备编号 property 
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
     * Object:保险申报明细's 设备类型property 
     */
    public String getEquType()
    {
        return getString("equType");
    }
    public void setEquType(String item)
    {
        setString("equType", item);
    }
    /**
     * Object:保险申报明细's 设备名称property 
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
     * Object:保险申报明细's 用户设备编号property 
     */
    public String getUserEquNumber()
    {
        return getString("userEquNumber");
    }
    public void setUserEquNumber(String item)
    {
        setString("userEquNumber", item);
    }
    /**
     * Object:保险申报明细's 规格型号property 
     */
    public String getSpecModel()
    {
        return getString("specModel");
    }
    public void setSpecModel(String item)
    {
        setString("specModel", item);
    }
    /**
     * Object:保险申报明细's 吨位property 
     */
    public String getTonnage()
    {
        return getString("tonnage");
    }
    public void setTonnage(String item)
    {
        setString("tonnage", item);
    }
    /**
     * Object:保险申报明细's 制造单位property 
     */
    public String getMakeUnit()
    {
        return getString("makeUnit");
    }
    public void setMakeUnit(String item)
    {
        setString("makeUnit", item);
    }
    /**
     * Object:保险申报明细's 原值property 
     */
    public java.math.BigDecimal getOriginalValue()
    {
        return getBigDecimal("originalValue");
    }
    public void setOriginalValue(java.math.BigDecimal item)
    {
        setBigDecimal("originalValue", item);
    }
    /**
     * Object:保险申报明细's 现值 property 
     */
    public java.math.BigDecimal getPresentValue()
    {
        return getBigDecimal("presentValue");
    }
    public void setPresentValue(java.math.BigDecimal item)
    {
        setBigDecimal("presentValue", item);
    }
    /**
     * Object:保险申报明细's 投保价值 property 
     */
    public java.math.BigDecimal getInsuredValue()
    {
        return getBigDecimal("insuredValue");
    }
    public void setInsuredValue(java.math.BigDecimal item)
    {
        setBigDecimal("insuredValue", item);
    }
    /**
     * Object:保险申报明细's 备注property 
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
     * Object:保险申报明细's 出厂投用日期property 
     */
    public java.util.Date getFactoryUseDate()
    {
        return getDate("factoryUseDate");
    }
    public void setFactoryUseDate(java.util.Date item)
    {
        setDate("factoryUseDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("047FBBF4");
    }
}