package com.kingdee.eas.port.markesupplier.subill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierStockEntryAttInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierStockEntryAttInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierStockEntryAttInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第3个表体 's null property 
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
     * Object:第3个表体's 类别编码property 
     */
    public String getAttNumber()
    {
        return getString("attNumber");
    }
    public void setAttNumber(String item)
    {
        setString("attNumber", item);
    }
    /**
     * Object:第3个表体's 类别名称property 
     */
    public String getAttName()
    {
        return getString("attName");
    }
    public void setAttName(String item)
    {
        setString("attName", item);
    }
    /**
     * Object:第3个表体's 附件列表property 
     */
    public String getAttlist()
    {
        return getString("attlist");
    }
    public void setAttlist(String item)
    {
        setString("attlist", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4192BD9D");
    }
}