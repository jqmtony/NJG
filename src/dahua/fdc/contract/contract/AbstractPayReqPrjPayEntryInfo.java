package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayReqPrjPayEntryInfo extends com.kingdee.eas.fdc.contract.PrjPayEntryBaseInfo implements Serializable 
{
    public AbstractPayReqPrjPayEntryInfo()
    {
        this("id");
    }
    protected AbstractPayReqPrjPayEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款申请工程情况表 's 申请单 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("766F91E1");
    }
}