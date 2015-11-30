package com.kingdee.eas.fdc.contract.settle;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettleDeclarationBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSettleDeclarationBillEntryInfo()
    {
        this("id");
    }
    protected AbstractSettleDeclarationBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
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
        return new BOSObjectType("7D67211E");
    }
}