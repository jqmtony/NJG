package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDeductAccountEntrysInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDeductAccountEntrysInfo()
    {
        this("id");
    }
    protected AbstractDeductAccountEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:扣款类型对应科目's nullproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object: 扣款类型对应科目 's 扣款对应科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("account");
    }
    public void setAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("account", item);
    }
    /**
     * Object: 扣款类型对应科目 's 扣款类型 property 
     */
    public com.kingdee.eas.fdc.basedata.DeductTypeInfo getDeductType()
    {
        return (com.kingdee.eas.fdc.basedata.DeductTypeInfo)get("deductType");
    }
    public void setDeductType(com.kingdee.eas.fdc.basedata.DeductTypeInfo item)
    {
        put("deductType", item);
    }
    /**
     * Object: 扣款类型对应科目 's 分录表头 property 
     */
    public com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E5665E99");
    }
}