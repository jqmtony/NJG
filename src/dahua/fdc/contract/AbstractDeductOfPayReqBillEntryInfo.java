package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDeductOfPayReqBillEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDeductOfPayReqBillEntryInfo()
    {
        this("id");
    }
    protected AbstractDeductOfPayReqBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ·ÖÂ¼ 's ¸¸ property 
     */
    public com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ·ÖÂ¼ 's ·ÖÂ¼ property 
     */
    public com.kingdee.eas.fdc.finance.DeductBillEntryInfo getDeductBillEntry()
    {
        return (com.kingdee.eas.fdc.finance.DeductBillEntryInfo)get("deductBillEntry");
    }
    public void setDeductBillEntry(com.kingdee.eas.fdc.finance.DeductBillEntryInfo item)
    {
        put("deductBillEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("97C05080");
    }
}