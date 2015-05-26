package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRichInvoiceRequestEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRichInvoiceRequestEntryInfo()
    {
        this("id");
    }
    protected AbstractRichInvoiceRequestEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo getParent()
    {
        return (com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 分录 's 到检单号 property 
     */
    public com.kingdee.eas.custom.richinf.RichExamedInfo getDjd()
    {
        return (com.kingdee.eas.custom.richinf.RichExamedInfo)get("djd");
    }
    public void setDjd(com.kingdee.eas.custom.richinf.RichExamedInfo item)
    {
        put("djd", item);
    }
    /**
     * Object:分录's 到检机构property 
     */
    public String getDjjg()
    {
        return getString("djjg");
    }
    public void setDjjg(String item)
    {
        setString("djjg", item);
    }
    /**
     * Object:分录's 结算金额property 
     */
    public java.math.BigDecimal getYsAmount()
    {
        return getBigDecimal("ysAmount");
    }
    public void setYsAmount(java.math.BigDecimal item)
    {
        setBigDecimal("ysAmount", item);
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
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A4EA17A6");
    }
}