package com.kingdee.eas.port.markesupplier.subase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierAttachListTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractMarketSupplierAttachListTreeInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierAttachListTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 档案附件清单组别 's 父节点 property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSupplierAttachListTreeInfo getParent()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSupplierAttachListTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.markesupplier.subase.MarketSupplierAttachListTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("20AADC9F");
    }
}