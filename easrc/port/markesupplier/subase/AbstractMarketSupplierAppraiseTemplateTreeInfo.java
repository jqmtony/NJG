package com.kingdee.eas.port.markesupplier.subase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierAppraiseTemplateTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractMarketSupplierAppraiseTemplateTreeInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierAppraiseTemplateTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评审模板组别 's 父节点 property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateTreeInfo getParent()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("60D2F4A1");
    }
}