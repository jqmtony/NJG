package com.kingdee.eas.port.equipment.insurance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInsuranceCoverageE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInsuranceCoverageE1Info()
    {
        this("id");
    }
    protected AbstractInsuranceCoverageE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��1������ 's null property 
     */
    public com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��1������ 's ʹ�õ�λ property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useUnit");
    }
    public void setUseUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useUnit", item);
    }
    /**
     * Object: ��1������ 's �豸��� property 
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
     * Object:��1������'s �豸����property 
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
     * Object:��1������'s �豸����property 
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
     * Object:��1������'s ����ͺ�property 
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
     * Object:��1������'s ����Ͷ������property 
     */
    public java.util.Date getFactoryUseDate()
    {
        return getDate("factoryUseDate");
    }
    public void setFactoryUseDate(java.util.Date item)
    {
        setDate("factoryUseDate", item);
    }
    /**
     * Object:��1������'s ���쵥λproperty 
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
     * Object:��1������'s ��λproperty 
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
     * Object:��1������'s ԭֵproperty 
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
     * Object:��1������'s ��ֵproperty 
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
     * Object:��1������'s Ͷ�����property 
     */
    public java.math.BigDecimal getInsuranceAmount()
    {
        return getBigDecimal("insuranceAmount");
    }
    public void setInsuranceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("insuranceAmount", item);
    }
    /**
     * Object:��1������'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("64E10F65");
    }
}