package com.kingdee.eas.fdc.basedata;

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
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
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
     * Object:��ͬ����'s �Ƿ��ܹ���������property 
     */
    public boolean isIsControlByQuanlity()
    {
        return getBoolean("isControlByQuanlity");
    }
    public void setIsControlByQuanlity(boolean item)
    {
        setBoolean("isControlByQuanlity", item);
    }
    /**
     * Object:��ͬ����'s �Ƿ������Լ�滮property 
     */
    public boolean isIsRefProgram()
    {
        return getBoolean("isRefProgram");
    }
    public void setIsRefProgram(boolean item)
    {
        setBoolean("isRefProgram", item);
    }
    /**
     * Object:��ͬ����'s ������ȷ��property 
     */
    public boolean isIsWorkLoadConfirm()
    {
        return getBoolean("isWorkLoadConfirm");
    }
    public void setIsWorkLoadConfirm(boolean item)
    {
        setBoolean("isWorkLoadConfirm", item);
    }
    /**
     * Object:��ͬ����'s ��������property 
     */
    public String getHelperNumber()
    {
        return getString("helperNumber");
    }
    public void setHelperNumber(String item)
    {
        setString("helperNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B371775E");
    }
}