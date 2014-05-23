package com.kingdee.eas.port.markesupplier.subase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierInvoiceTypeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractSupplierInvoiceTypeInfo()
    {
        this("id");
    }
    protected AbstractSupplierInvoiceTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 供应商类别（新） 's 组别 property 
     */
    public com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeInfo getTreeid()
    {
        return (com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object:供应商类别（新）'s 是否启用property 
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
     * Object:供应商类别（新）'s 备注：property 
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
        return new BOSObjectType("BFCCD445");
    }
}