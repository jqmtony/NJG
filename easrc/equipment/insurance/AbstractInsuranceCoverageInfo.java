package com.kingdee.eas.port.equipment.insurance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInsuranceCoverageInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractInsuranceCoverageInfo()
    {
        this("id");
    }
    protected AbstractInsuranceCoverageInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.equipment.insurance.InsuranceCoverageE1Collection());
    }
    /**
     * Object: ����Ͷ����ϸ�� 's ���� property 
     */
    public com.kingdee.eas.port.equipment.base.InsuranceInfo getInsurance()
    {
        return (com.kingdee.eas.port.equipment.base.InsuranceInfo)get("insurance");
    }
    public void setInsurance(com.kingdee.eas.port.equipment.base.InsuranceInfo item)
    {
        put("insurance", item);
    }
    /**
     * Object: ����Ͷ����ϸ�� 's ���չ�˾ property 
     */
    public com.kingdee.eas.port.equipment.base.InsuranceCompanyInfo getInsuranceCompany()
    {
        return (com.kingdee.eas.port.equipment.base.InsuranceCompanyInfo)get("insuranceCompany");
    }
    public void setInsuranceCompany(com.kingdee.eas.port.equipment.base.InsuranceCompanyInfo item)
    {
        put("insuranceCompany", item);
    }
    /**
     * Object: ����Ͷ����ϸ�� 's ��1������ property 
     */
    public com.kingdee.eas.port.equipment.insurance.InsuranceCoverageE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.insurance.InsuranceCoverageE1Collection)get("E1");
    }
    /**
     * Object:����Ͷ����ϸ��'s ����IDproperty 
     */
    public String getXianzhongID()
    {
        return getString("xianzhongID");
    }
    public void setXianzhongID(String item)
    {
        setString("xianzhongID", item);
    }
    /**
     * Object:����Ͷ����ϸ��'s Ͷ������property 
     */
    public String getCoverNumber()
    {
        return getString("coverNumber");
    }
    public void setCoverNumber(String item)
    {
        setString("coverNumber", item);
    }
    /**
     * Object:����Ͷ����ϸ��'s ��ͬ����property 
     */
    public String getContNumber()
    {
        return getString("contNumber");
    }
    public void setContNumber(String item)
    {
        setString("contNumber", item);
    }
    /**
     * Object:����Ͷ����ϸ��'s ������property 
     */
    public java.util.Date getEffectDate()
    {
        return getDate("effectDate");
    }
    public void setEffectDate(java.util.Date item)
    {
        setDate("effectDate", item);
    }
    /**
     * Object:����Ͷ����ϸ��'s �ձ�����property 
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
     * Object:����Ͷ����ϸ��'s ���property 
     */
    public String getYear()
    {
        return getString("year");
    }
    public void setYear(String item)
    {
        setString("year", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("46F6E919");
    }
}