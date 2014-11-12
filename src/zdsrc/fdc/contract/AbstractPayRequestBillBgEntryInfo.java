package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayRequestBillBgEntryInfo extends com.kingdee.eas.fdc.contract.PayBgEntryInfo implements Serializable 
{
    public AbstractPayRequestBillBgEntryInfo()
    {
        this("id");
    }
    protected AbstractPayRequestBillBgEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款申请单费用清单 's 付款申请单 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:付款申请单费用清单's 实付金额property 
     */
    public java.math.BigDecimal getActPayAmount()
    {
        return getBigDecimal("actPayAmount");
    }
    public void setActPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actPayAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BAA74684");
    }
}