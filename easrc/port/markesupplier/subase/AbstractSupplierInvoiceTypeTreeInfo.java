package com.kingdee.eas.port.markesupplier.subase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierInvoiceTypeTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractSupplierInvoiceTypeTreeInfo()
    {
        this("id");
    }
    protected AbstractSupplierInvoiceTypeTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 供应商类别（新）组别 's 父节点 property 
     */
    public com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeInfo getParent()
    {
        return (com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A9371183");
    }
}