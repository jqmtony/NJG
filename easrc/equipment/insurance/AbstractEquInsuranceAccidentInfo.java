package com.kingdee.eas.port.equipment.insurance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEquInsuranceAccidentInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractEquInsuranceAccidentInfo()
    {
        this("id");
    }
    protected AbstractEquInsuranceAccidentInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 设备保险事故记录 's 设备编号 property 
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
     * Object:设备保险事故记录's 设备名称property 
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
     * Object: 设备保险事故记录 's 保险公司 property 
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
     * Object: 设备保险事故记录 's 保单号 property 
     */
    public com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo getPolicyNumber()
    {
        return (com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo)get("policyNumber");
    }
    public void setPolicyNumber(com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo item)
    {
        put("policyNumber", item);
    }
    /**
     * Object: 设备保险事故记录 's 险种 property 
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
     * Object:设备保险事故记录's 出险日期property 
     */
    public java.util.Date getLossDate()
    {
        return getDate("lossDate");
    }
    public void setLossDate(java.util.Date item)
    {
        setDate("lossDate", item);
    }
    /**
     * Object:设备保险事故记录's 预计损失金额property 
     */
    public java.math.BigDecimal getExpectedLoss()
    {
        return getBigDecimal("expectedLoss");
    }
    public void setExpectedLoss(java.math.BigDecimal item)
    {
        setBigDecimal("expectedLoss", item);
    }
    /**
     * Object:设备保险事故记录's 理赔金额property 
     */
    public java.math.BigDecimal getClaimAmount()
    {
        return getBigDecimal("claimAmount");
    }
    public void setClaimAmount(java.math.BigDecimal item)
    {
        setBigDecimal("claimAmount", item);
    }
    /**
     * Object:设备保险事故记录's 事故说明property 
     */
    public String getDescriptionIncident()
    {
        return getString("descriptionIncident");
    }
    public void setDescriptionIncident(String item)
    {
        setString("descriptionIncident", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("85931329");
    }
}