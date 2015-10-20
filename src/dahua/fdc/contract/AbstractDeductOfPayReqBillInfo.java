package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDeductOfPayReqBillInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDeductOfPayReqBillInfo()
    {
        this("id");
    }
    protected AbstractDeductOfPayReqBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryCollection());
    }
    /**
     * Object: 付款申请单对应的扣款项 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryCollection)get("entrys");
    }
    /**
     * Object: 付款申请单对应的扣款项 's 扣款类型 property 
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
     * Object:付款申请单对应的扣款项's 金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:付款申请单对应的扣款项's 累计申请额property 
     */
    public java.math.BigDecimal getAllReqAmt()
    {
        return getBigDecimal("allReqAmt");
    }
    public void setAllReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("allReqAmt", item);
    }
    /**
     * Object:付款申请单对应的扣款项's 累计实付property 
     */
    public java.math.BigDecimal getAllPaidAmt()
    {
        return getBigDecimal("allPaidAmt");
    }
    public void setAllPaidAmt(java.math.BigDecimal item)
    {
        setBigDecimal("allPaidAmt", item);
    }
    /**
     * Object:付款申请单对应的扣款项's 是否实付property 
     */
    public boolean isHasPaid()
    {
        return getBoolean("hasPaid");
    }
    public void setHasPaid(boolean item)
    {
        setBoolean("hasPaid", item);
    }
    /**
     * Object: 付款申请单对应的扣款项 's 付款申请单 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getPayRequestBill()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("payRequestBill");
    }
    public void setPayRequestBill(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("payRequestBill", item);
    }
    /**
     * Object: 付款申请单对应的扣款项 's 付款单 property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillInfo getPaymentBill()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillInfo)get("paymentBill");
    }
    public void setPaymentBill(com.kingdee.eas.fi.cas.PaymentBillInfo item)
    {
        put("paymentBill", item);
    }
    /**
     * Object:付款申请单对应的扣款项's 原币金额property 
     */
    public java.math.BigDecimal getOriginalAmount()
    {
        return getBigDecimal("originalAmount");
    }
    public void setOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("originalAmount", item);
    }
    /**
     * Object:付款申请单对应的扣款项's 累计申请额原币property 
     */
    public java.math.BigDecimal getAllReqOriAmt()
    {
        return getBigDecimal("allReqOriAmt");
    }
    public void setAllReqOriAmt(java.math.BigDecimal item)
    {
        setBigDecimal("allReqOriAmt", item);
    }
    /**
     * Object:付款申请单对应的扣款项's 累计实付原币property 
     */
    public java.math.BigDecimal getAllPaidOriAmt()
    {
        return getBigDecimal("allPaidOriAmt");
    }
    public void setAllPaidOriAmt(java.math.BigDecimal item)
    {
        setBigDecimal("allPaidOriAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1E8CE312");
    }
}