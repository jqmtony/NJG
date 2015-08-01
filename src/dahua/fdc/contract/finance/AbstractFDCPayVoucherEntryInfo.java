package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCPayVoucherEntryInfo extends com.kingdee.eas.fdc.finance.FDCBaseVoucherEntryInfo implements Serializable 
{
    public AbstractFDCPayVoucherEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCPayVoucherEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房地产付款分录 's 付款单 property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillInfo getParent()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fi.cas.PaymentBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:房地产付款分录's 付款原币property 
     */
    public java.math.BigDecimal getPayAmount()
    {
        return getBigDecimal("payAmount");
    }
    public void setPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payAmount", item);
    }
    /**
     * Object:房地产付款分录's 付款本币property 
     */
    public java.math.BigDecimal getLocatePayAmount()
    {
        return getBigDecimal("locatePayAmount");
    }
    public void setLocatePayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("locatePayAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AF19A856");
    }
}