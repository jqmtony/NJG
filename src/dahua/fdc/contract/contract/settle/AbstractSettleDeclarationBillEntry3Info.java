package com.kingdee.eas.fdc.contract.settle;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettleDeclarationBillEntry3Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSettleDeclarationBillEntry3Info()
    {
        this("id");
    }
    protected AbstractSettleDeclarationBillEntry3Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第3个表体 's null property 
     */
    public com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2F7D02D5");
    }
}