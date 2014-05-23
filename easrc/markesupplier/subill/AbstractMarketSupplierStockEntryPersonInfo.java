package com.kingdee.eas.port.markesupplier.subill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierStockEntryPersonInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierStockEntryPersonInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierStockEntryPersonInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��2������ 's null property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo getParent()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��2������'s ����property 
     */
    public String getPersonName()
    {
        return getString("personName");
    }
    public void setPersonName(String item)
    {
        setString("personName", item);
    }
    /**
     * Object:��2������'s ְ��property 
     */
    public String getPosition()
    {
        return getString("position");
    }
    public void setPosition(String item)
    {
        setString("position", item);
    }
    /**
     * Object:��2������'s �ֻ�property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    /**
     * Object:��2������'s �칫�绰property 
     */
    public String getWorkPhone()
    {
        return getString("workPhone");
    }
    public void setWorkPhone(String item)
    {
        setString("workPhone", item);
    }
    /**
     * Object:��2������'s ����property 
     */
    public String getPersonFax()
    {
        return getString("personFax");
    }
    public void setPersonFax(String item)
    {
        setString("personFax", item);
    }
    /**
     * Object:��2������'s �Ƿ�Ϊ��Ȩ��ϵ��property 
     */
    public boolean isIsDefault()
    {
        return getBoolean("isDefault");
    }
    public void setIsDefault(boolean item)
    {
        setBoolean("isDefault", item);
    }
    /**
     * Object:��2������'s ����property 
     */
    public String getEmail()
    {
        return getString("email");
    }
    public void setEmail(String item)
    {
        setString("email", item);
    }
    /**
     * Object:��2������'s ��ϵ��ʽproperty 
     */
    public String getContact()
    {
        return getString("contact");
    }
    public void setContact(String item)
    {
        setString("contact", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EC25A6D9");
    }
}