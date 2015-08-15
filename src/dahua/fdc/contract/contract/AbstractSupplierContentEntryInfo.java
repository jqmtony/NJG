package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierContentEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierContentEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierContentEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 执行内容 's 单据头 property 
     */
    public com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 执行内容 's 执行内容 property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo getContent()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo)get("content");
    }
    public void setContent(com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo item)
    {
        put("content", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0F7EDA20");
    }
}