package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaxManagerInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTaxManagerInfo()
    {
        this("id");
    }
    protected AbstractTaxManagerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:税务登记信息's 国税登记号property 
     */
    public String getCountryTaxNum()
    {
        return getCountryTaxNum((Locale)null);
    }
    public void setCountryTaxNum(String item)
    {
		setCountryTaxNum(item,(Locale)null);
    }
    public String getCountryTaxNum(Locale local)
    {
        return TypeConversionUtils.objToString(get("countryTaxNum", local));
    }
    public void setCountryTaxNum(String item, Locale local)
    {
        put("countryTaxNum", item, local);
    }
    /**
     * Object:税务登记信息's 地税登记号property 
     */
    public String getAreaTaxNum()
    {
        return getAreaTaxNum((Locale)null);
    }
    public void setAreaTaxNum(String item)
    {
		setAreaTaxNum(item,(Locale)null);
    }
    public String getAreaTaxNum(Locale local)
    {
        return TypeConversionUtils.objToString(get("areaTaxNum", local));
    }
    public void setAreaTaxNum(String item, Locale local)
    {
        put("areaTaxNum", item, local);
    }
    /**
     * Object:税务登记信息's 纳税人类别property 
     */
    public com.kingdee.eas.fdc.basedata.TaxPayerTypeEnum getTaxPayerType()
    {
        return com.kingdee.eas.fdc.basedata.TaxPayerTypeEnum.getEnum(getString("taxPayerType"));
    }
    public void setTaxPayerType(com.kingdee.eas.fdc.basedata.TaxPayerTypeEnum item)
    {
		if (item != null) {
        setString("taxPayerType", item.getValue());
		}
    }
    /**
     * Object:税务登记信息's 银行帐号property 
     */
    public String getAccountBank()
    {
        return getAccountBank((Locale)null);
    }
    public void setAccountBank(String item)
    {
		setAccountBank(item,(Locale)null);
    }
    public String getAccountBank(Locale local)
    {
        return TypeConversionUtils.objToString(get("accountBank", local));
    }
    public void setAccountBank(String item, Locale local)
    {
        put("accountBank", item, local);
    }
    /**
     * Object:税务登记信息's 银行property 
     */
    public String getBankNum()
    {
        return getBankNum((Locale)null);
    }
    public void setBankNum(String item)
    {
		setBankNum(item,(Locale)null);
    }
    public String getBankNum(Locale local)
    {
        return TypeConversionUtils.objToString(get("bankNum", local));
    }
    public void setBankNum(String item, Locale local)
    {
        put("bankNum", item, local);
    }
    /**
     * Object: 税务登记信息 's 开户银行 property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("bank");
    }
    public void setBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("bank", item);
    }
    /**
     * Object: 税务登记信息 's 父 property 
     */
    public com.kingdee.eas.fdc.basedata.UnitDataManagerInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.UnitDataManagerInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.UnitDataManagerInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("50ED91F4");
    }
}