package com.kingdee.eas.port.markesupplier.subill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierStockEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierStockEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierStockEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
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
     * Object:��¼'s ������property 
     */
    public String getTypeNumber()
    {
        return getString("typeNumber");
    }
    public void setTypeNumber(String item)
    {
        setString("typeNumber", item);
    }
    /**
     * Object:��¼'s �������property 
     */
    public String getTypeName()
    {
        return getString("typeName");
    }
    public void setTypeName(String item)
    {
        setString("typeName", item);
    }
    /**
     * Object:��¼'s ����property 
     */
    public int getPeopleSum()
    {
        return getInt("peopleSum");
    }
    public void setPeopleSum(int item)
    {
        setInt("peopleSum", item);
    }
    /**
     * Object:��¼'s ��ע��property 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D4717A64");
    }
}