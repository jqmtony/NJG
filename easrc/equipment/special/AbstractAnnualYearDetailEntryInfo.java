package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAnnualYearDetailEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractAnnualYearDetailEntryInfo()
    {
        this("id");
    }
    protected AbstractAnnualYearDetailEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ȼ����ϸ 's null property 
     */
    public com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��ȼ����ϸ 's �豸������ property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getZdaNumber()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("zdaNumber");
    }
    public void setZdaNumber(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("zdaNumber", item);
    }
    /**
     * Object:��ȼ����ϸ's �豸����property 
     */
    public String getEquipmentName()
    {
        return getString("equipmentName");
    }
    public void setEquipmentName(String item)
    {
        setString("equipmentName", item);
    }
    /**
     * Object:��ȼ����ϸ's ע�����property 
     */
    public String getCode()
    {
        return getString("code");
    }
    public void setCode(String item)
    {
        setString("code", item);
    }
    /**
     * Object:��ȼ����ϸ's ʹ�õ�λproperty 
     */
    public String getUseUnit()
    {
        return getString("useUnit");
    }
    public void setUseUnit(String item)
    {
        setString("useUnit", item);
    }
    /**
     * Object:��ȼ����ϸ's �ƻ���������property 
     */
    public java.util.Date getPlanDate()
    {
        return getDate("planDate");
    }
    public void setPlanDate(java.util.Date item)
    {
        setDate("planDate", item);
    }
    /**
     * Object:��ȼ����ϸ's ���ڼ�������property 
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
     * Object:��ȼ����ϸ's ʹ��״̬property 
     */
    public com.kingdee.eas.port.equipment.base.enumbase.sbStatusType getState()
    {
        return com.kingdee.eas.port.equipment.base.enumbase.sbStatusType.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.port.equipment.base.enumbase.sbStatusType item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:��ȼ����ϸ's ʹ�õص�property 
     */
    public String getAddress()
    {
        return getString("address");
    }
    public void setAddress(String item)
    {
        setString("address", item);
    }
    /**
     * Object:��ȼ����ϸ's �û�ʹ�ñ��property 
     */
    public String getCompanyNumber()
    {
        return getString("companyNumber");
    }
    public void setCompanyNumber(String item)
    {
        setString("companyNumber", item);
    }
    /**
     * Object:��ȼ����ϸ's ����ͺ�property 
     */
    public String getNO()
    {
        return getString("NO");
    }
    public void setNO(String item)
    {
        setString("NO", item);
    }
    /**
     * Object:��ȼ����ϸ's ���������property 
     */
    public String getEngineNumber()
    {
        return getString("engineNumber");
    }
    public void setEngineNumber(String item)
    {
        setString("engineNumber", item);
    }
    /**
     * Object:��ȼ����ϸ's ���ܺ�property 
     */
    public String getCarNumber()
    {
        return getString("carNumber");
    }
    public void setCarNumber(String item)
    {
        setString("carNumber", item);
    }
    /**
     * Object:��ȼ����ϸ's ���������(t)property 
     */
    public java.math.BigDecimal getWeight()
    {
        return getBigDecimal("weight");
    }
    public void setWeight(java.math.BigDecimal item)
    {
        setBigDecimal("weight", item);
    }
    /**
     * Object:��ȼ����ϸ's Ͷ������property 
     */
    public java.util.Date getUseDate()
    {
        return getDate("useDate");
    }
    public void setUseDate(java.util.Date item)
    {
        setDate("useDate", item);
    }
    /**
     * Object:��ȼ����ϸ's ���쵥λproperty 
     */
    public String getCreateUnit()
    {
        return getString("createUnit");
    }
    public void setCreateUnit(String item)
    {
        setString("createUnit", item);
    }
    /**
     * Object:��ȼ����ϸ's �������property 
     */
    public com.kingdee.eas.port.equipment.base.enumbase.CheckType getCheckType()
    {
        return com.kingdee.eas.port.equipment.base.enumbase.CheckType.getEnum(getString("checkType"));
    }
    public void setCheckType(com.kingdee.eas.port.equipment.base.enumbase.CheckType item)
    {
		if (item != null) {
        setString("checkType", item.getValue());
		}
    }
    /**
     * Object:��ȼ����ϸ's ��עproperty 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    /**
     * Object:��ȼ����ϸ's �Ƿ���property 
     */
    public boolean isCheck()
    {
        return getBoolean("check");
    }
    public void setCheck(boolean item)
    {
        setBoolean("check", item);
    }
    /**
     * Object:��ȼ����ϸ's �����property 
     */
    public com.kingdee.eas.port.equipment.base.enumbase.CheckResult getResult()
    {
        return com.kingdee.eas.port.equipment.base.enumbase.CheckResult.getEnum(getString("result"));
    }
    public void setResult(com.kingdee.eas.port.equipment.base.enumbase.CheckResult item)
    {
		if (item != null) {
        setString("result", item.getValue());
		}
    }
    /**
     * Object:��ȼ����ϸ's ʵ�ʼ������property 
     */
    public java.util.Date getSjjcrq()
    {
        return getDate("sjjcrq");
    }
    public void setSjjcrq(java.util.Date item)
    {
        setDate("sjjcrq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1022BC1B");
    }
}