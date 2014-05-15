package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOpenRegistrationEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOpenRegistrationEntryInfo()
    {
        this("id");
    }
    protected AbstractOpenRegistrationEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �б굥λ��Ϣ 's null property 
     */
    public com.kingdee.eas.port.pm.invite.OpenRegistrationInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invite.OpenRegistrationInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invite.OpenRegistrationInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�б굥λ��Ϣ's ����property 
     */
    public String getQuality()
    {
        return getString("quality");
    }
    public void setQuality(String item)
    {
        setString("quality", item);
    }
    /**
     * Object:�б굥λ��Ϣ's �Ƿ��ѽ�Ͷ�걣֤��property 
     */
    public boolean isPayDeposit()
    {
        return getBoolean("payDeposit");
    }
    public void setPayDeposit(boolean item)
    {
        setBoolean("payDeposit", item);
    }
    /**
     * Object:�б굥λ��Ϣ's Ͷ�걣֤��property 
     */
    public String getDeposit()
    {
        return getString("deposit");
    }
    public void setDeposit(String item)
    {
        setString("deposit", item);
    }
    /**
     * Object:�б굥λ��Ϣ's Ͷ�걨��property 
     */
    public String getQuotedPrice()
    {
        return getString("quotedPrice");
    }
    public void setQuotedPrice(String item)
    {
        setString("quotedPrice", item);
    }
    /**
     * Object:�б굥λ��Ϣ's ����˵��property 
     */
    public String getDirections()
    {
        return getString("directions");
    }
    public void setDirections(String item)
    {
        setString("directions", item);
    }
    /**
     * Object:�б굥λ��Ϣ's ��עproperty 
     */
    public String getComment()
    {
        return getString("comment");
    }
    public void setComment(String item)
    {
        setString("comment", item);
    }
    /**
     * Object:�б굥λ��Ϣ's ��ϵ��property 
     */
    public String getContact()
    {
        return getString("contact");
    }
    public void setContact(String item)
    {
        setString("contact", item);
    }
    /**
     * Object:�б굥λ��Ϣ's �绰����property 
     */
    public String getTelephone()
    {
        return getString("telephone");
    }
    public void setTelephone(String item)
    {
        setString("telephone", item);
    }
    /**
     * Object:�б굥λ��Ϣ's ����(��)property 
     */
    public String getPrjPeriod()
    {
        return getString("prjPeriod");
    }
    public void setPrjPeriod(String item)
    {
        setString("prjPeriod", item);
    }
    /**
     * Object: �б굥λ��Ϣ 's ��Ӧ������ property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo getSupplierName()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo)get("supplierName");
    }
    public void setSupplierName(com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo item)
    {
        put("supplierName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3DE52066");
    }
}