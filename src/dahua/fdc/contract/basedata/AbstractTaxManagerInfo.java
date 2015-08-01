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
     * Object:˰��Ǽ���Ϣ's ��˰�ǼǺ�property 
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
     * Object:˰��Ǽ���Ϣ's ��˰�ǼǺ�property 
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
     * Object:˰��Ǽ���Ϣ's ��˰�����property 
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
     * Object:˰��Ǽ���Ϣ's �����ʺ�property 
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
     * Object:˰��Ǽ���Ϣ's ����property 
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
     * Object: ˰��Ǽ���Ϣ 's �������� property 
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
     * Object: ˰��Ǽ���Ϣ 's �� property 
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