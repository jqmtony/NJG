package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractLicenceManagerInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractLicenceManagerInfo()
    {
        this("id");
    }
    protected AbstractLicenceManagerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:营业执照信息管理's 营业执照号property 
     */
    public String getLicenseNumber()
    {
        return getLicenseNumber((Locale)null);
    }
    public void setLicenseNumber(String item)
    {
		setLicenseNumber(item,(Locale)null);
    }
    public String getLicenseNumber(Locale local)
    {
        return TypeConversionUtils.objToString(get("licenseNumber", local));
    }
    public void setLicenseNumber(String item, Locale local)
    {
        put("licenseNumber", item, local);
    }
    /**
     * Object:营业执照信息管理's 公司经营地址property 
     */
    public String getBusinessAddress()
    {
        return getBusinessAddress((Locale)null);
    }
    public void setBusinessAddress(String item)
    {
		setBusinessAddress(item,(Locale)null);
    }
    public String getBusinessAddress(Locale local)
    {
        return TypeConversionUtils.objToString(get("businessAddress", local));
    }
    public void setBusinessAddress(String item, Locale local)
    {
        put("businessAddress", item, local);
    }
    /**
     * Object:营业执照信息管理's 注册资本property 
     */
    public java.math.BigDecimal getRegisterCapital()
    {
        return getBigDecimal("registerCapital");
    }
    public void setRegisterCapital(java.math.BigDecimal item)
    {
        setBigDecimal("registerCapital", item);
    }
    /**
     * Object:营业执照信息管理's 实收资本property 
     */
    public java.math.BigDecimal getRealCapital()
    {
        return getBigDecimal("realCapital");
    }
    public void setRealCapital(java.math.BigDecimal item)
    {
        setBigDecimal("realCapital", item);
    }
    /**
     * Object:营业执照信息管理's 营业期限开始日期property 
     */
    public java.util.Date getBusinessDateFrom()
    {
        return getDate("businessDateFrom");
    }
    public void setBusinessDateFrom(java.util.Date item)
    {
        setDate("businessDateFrom", item);
    }
    /**
     * Object:营业执照信息管理's 营业期限结束日期property 
     */
    public java.util.Date getBusinessDateTo()
    {
        return getDate("businessDateTo");
    }
    public void setBusinessDateTo(java.util.Date item)
    {
        setDate("businessDateTo", item);
    }
    /**
     * Object:营业执照信息管理's 成立日期property 
     */
    public java.util.Date getBuildDate()
    {
        return getDate("buildDate");
    }
    public void setBuildDate(java.util.Date item)
    {
        setDate("buildDate", item);
    }
    /**
     * Object:营业执照信息管理's 登记日期property 
     */
    public java.util.Date getRegisterDate()
    {
        return getDate("registerDate");
    }
    public void setRegisterDate(java.util.Date item)
    {
        setDate("registerDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("16421F8E");
    }
}