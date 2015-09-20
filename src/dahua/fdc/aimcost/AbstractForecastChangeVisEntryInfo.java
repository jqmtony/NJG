package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractForecastChangeVisEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractForecastChangeVisEntryInfo()
    {
        this("id");
    }
    protected AbstractForecastChangeVisEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��¼'s ��������property 
     */
    public String getItemName()
    {
        return getString("itemName");
    }
    public void setItemName(String item)
    {
        setString("itemName", item);
    }
    /**
     * Object:��¼'s Ԥ�����property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:��¼'s ����˵��property 
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
        return new BOSObjectType("42F3CCF4");
    }
}