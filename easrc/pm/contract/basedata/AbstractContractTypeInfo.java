package com.kingdee.eas.port.pm.contract.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractContractTypeInfo()
    {
        this("id");
    }
    protected AbstractContractTypeInfo(String pkField)
    {
        super(pkField);
        put("contractWFTypeEntry", new com.kingdee.eas.port.pm.contract.basedata.ContractTypeContractWFTypeEntryCollection());
        put("entry", new com.kingdee.eas.port.pm.contract.basedata.ContractTypeEntryCollection());
    }
    /**
     * Object:��ͬ����'s ���û����״̬property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object:��ͬ����'s �Ƿ�ɱ������property 
     */
    public boolean isIsCost()
    {
        return getBoolean("isCost");
    }
    public void setIsCost(boolean item)
    {
        setBoolean("isCost", item);
    }
    /**
     * Object:��ͬ����'s �������property 
     */
    public java.math.BigDecimal getPayScale()
    {
        return getBigDecimal("payScale");
    }
    public void setPayScale(java.math.BigDecimal item)
    {
        setBigDecimal("payScale", item);
    }
    /**
     * Object: ��ͬ���� 's ����� property 
     */
    public com.kingdee.eas.port.pm.contract.basedata.ContractTypeInfo getParent()
    {
        return (com.kingdee.eas.port.pm.contract.basedata.ContractTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.contract.basedata.ContractTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��ͬ���� 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDutyOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("dutyOrgUnit");
    }
    public void setDutyOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("dutyOrgUnit", item);
    }
    /**
     * Object:��ͬ����'s ӡ��˰��property 
     */
    public java.math.BigDecimal getStampTaxRate()
    {
        return getBigDecimal("stampTaxRate");
    }
    public void setStampTaxRate(java.math.BigDecimal item)
    {
        setBigDecimal("stampTaxRate", item);
    }
    /**
     * Object:��ͬ����'s ������property 
     */
    public String getForSupportLongnumberCoding()
    {
        return getString("forSupportLongnumberCoding");
    }
    public void setForSupportLongnumberCoding(String item)
    {
        setString("forSupportLongnumberCoding", item);
    }
    /**
     * Object: ��ͬ���� 's ���������¼ property 
     */
    public com.kingdee.eas.port.pm.contract.basedata.ContractTypeEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.pm.contract.basedata.ContractTypeEntryCollection)get("entry");
    }
    /**
     * Object:��ͬ����'s �������̷�����֯property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum getOrgType()
    {
        return com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum.getEnum(getString("orgType"));
    }
    public void setOrgType(com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum item)
    {
		if (item != null) {
        setString("orgType", item.getValue());
		}
    }
    /**
     * Object: ��ͬ���� 's ��ͬ�������ͷ�¼ property 
     */
    public com.kingdee.eas.port.pm.contract.basedata.ContractTypeContractWFTypeEntryCollection getContractWFTypeEntry()
    {
        return (com.kingdee.eas.port.pm.contract.basedata.ContractTypeContractWFTypeEntryCollection)get("contractWFTypeEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("56F37ED9");
    }
}