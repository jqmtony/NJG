package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCopySupplierEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCopySupplierEntryInfo()
    {
        this("id");
    }
    protected AbstractCopySupplierEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 抄送单位分录 's 父 property 
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
     * Object: 抄送单位分录 's 抄送单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getCopySupp()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("copySupp");
    }
    public void setCopySupp(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("copySupp", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("169D3FB6");
    }
}