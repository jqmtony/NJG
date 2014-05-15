package com.kingdee.eas.port.markesupplier.subase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierAppraiseTemplateInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMarketSupplierAppraiseTemplateInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierAppraiseTemplateInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateEntryCollection());
    }
    /**
     * Object: 评审模板 's 组别 property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateTreeInfo getTreeid()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object:评审模板's 是否启用property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    /**
     * Object:评审模板's 备注：property 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    /**
     * Object: 评审模板 's 评审类型 property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo getAccreditationType()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo)get("AccreditationType");
    }
    public void setAccreditationType(com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo item)
    {
        put("AccreditationType", item);
    }
    /**
     * Object: 评审模板 's 第1个表体 property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateEntryCollection)get("Entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("71A2F663");
    }
}