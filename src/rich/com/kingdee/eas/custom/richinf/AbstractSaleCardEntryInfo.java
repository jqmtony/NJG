package com.kingdee.eas.custom.richinf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSaleCardEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSaleCardEntryInfo()
    {
        this("id");
    }
    protected AbstractSaleCardEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.custom.richinf.SaleCardInfo getParent()
    {
        return (com.kingdee.eas.custom.richinf.SaleCardInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.richinf.SaleCardInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6259986D");
    }
}