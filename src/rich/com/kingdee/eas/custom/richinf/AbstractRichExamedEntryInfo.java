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
     * Object: 分录 's 单据头 property 
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
     * Object:分录's 到检套餐编码property 
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
     * Object:分录's 到检套餐名称property 
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
     * Object: 分录 's 销售类别 property 
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
     * Object: 分录 's 收款类别 property 
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
     * Object:分录's 备注property 
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
     * Object:分录's 到检人property 
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
     * Object: 分录 's 到检人明细 property 
     */
    public com.kingdee.eas.custom.richinf.RichExamedEntryDjrentryCollection getDjrentry()
    {
        return (com.kingdee.eas.custom.richinf.RichExamedEntryDjrentryCollection)get("Djrentry");
    }
    /**
     * Object:分录's 结算金额property 
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
     * Object:分录's 业务单据编号property 
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