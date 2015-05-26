package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichExamedEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRichExamedEntryInfo()
    {
        this("id");
    }
    protected AbstractRichExamedEntryInfo(String pkField)
    {
        super(pkField);
        put("Djrentry", new com.kingdee.eas.custom.richinf.RichExamedEntryDjrentryCollection());
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.custom.richinf.RichExamedInfo getParent()
    {
        return (com.kingdee.eas.custom.richinf.RichExamedInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.richinf.RichExamedInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��¼'s �����ײͱ���property 
     */
    public String getDjtcNumber()
    {
        return getString("djtcNumber");
    }
    public void setDjtcNumber(String item)
    {
        setString("djtcNumber", item);
    }
    /**
     * Object:��¼'s �����ײ�����property 
     */
    public String getDjctName()
    {
        return getString("djctName");
    }
    public void setDjctName(String item)
    {
        setString("djctName", item);
    }
    /**
     * Object: ��¼ 's ������� property 
     */
    public com.kingdee.eas.custom.richbase.SaleTypeInfo getSlType()
    {
        return (com.kingdee.eas.custom.richbase.SaleTypeInfo)get("slType");
    }
    public void setSlType(com.kingdee.eas.custom.richbase.SaleTypeInfo item)
    {
        put("slType", item);
    }
    /**
     * Object: ��¼ 's �տ���� property 
     */
    public com.kingdee.eas.custom.richbase.ReceTypeInfo getSkType()
    {
        return (com.kingdee.eas.custom.richbase.ReceTypeInfo)get("skType");
    }
    public void setSkType(com.kingdee.eas.custom.richbase.ReceTypeInfo item)
    {
        put("skType", item);
    }
    /**
     * Object:��¼'s ��עproperty 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    /**
     * Object:��¼'s ������property 
     */
    public String getDjr()
    {
        return getString("djr");
    }
    public void setDjr(String item)
    {
        setString("djr", item);
    }
    /**
     * Object: ��¼ 's ��������ϸ property 
     */
    public com.kingdee.eas.custom.richinf.RichExamedEntryDjrentryCollection getDjrentry()
    {
        return (com.kingdee.eas.custom.richinf.RichExamedEntryDjrentryCollection)get("Djrentry");
    }
    /**
     * Object:��¼'s ������property 
     */
    public java.math.BigDecimal getJsAmount()
    {
        return getBigDecimal("jsAmount");
    }
    public void setJsAmount(java.math.BigDecimal item)
    {
        setBigDecimal("jsAmount", item);
    }
    /**
     * Object:��¼'s ҵ�񵥾ݱ��property 
     */
    public String getYwdjbh()
    {
        return getString("ywdjbh");
    }
    public void setYwdjbh(String item)
    {
        setString("ywdjbh", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0FC258CA");
    }
}