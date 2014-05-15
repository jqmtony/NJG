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
     * Object:保险投保明细表's 年度property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object: 保险投保明细表 's 险种 property 
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
     * Object: 保险投保明细表 's 保险公司 property 
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
     * Object: 保险投保明细表 's 第1个表体 property 
     */
    public com.kingdee.eas.port.equipment.insurance.InsuranceCoverageE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.insurance.InsuranceCoverageE1Collection)get("E1");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("46F6E919");
    }
}