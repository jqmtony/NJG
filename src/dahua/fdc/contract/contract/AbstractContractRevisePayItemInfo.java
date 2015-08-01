package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractRevisePayItemInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractRevisePayItemInfo()
    {
        this("id");
    }
    protected AbstractContractRevisePayItemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��ͬ�޶��տ�����'s ����property 
     */
    public java.util.Date getPayItemDate()
    {
        return getDate("payItemDate");
    }
    public void setPayItemDate(java.util.Date item)
    {
        setDate("payItemDate", item);
    }
    /**
     * Object:��ͬ�޶��տ�����'s ��������property 
     */
    public String getPayCondition()
    {
        return getString("payCondition");
    }
    public void setPayCondition(String item)
    {
        setString("payCondition", item);
    }
    /**
     * Object:��ͬ�޶��տ�����'s �������property 
     */
    public java.math.BigDecimal getProp()
    {
        return getBigDecimal("prop");
    }
    public void setProp(java.math.BigDecimal item)
    {
        setBigDecimal("prop", item);
    }
    /**
     * Object:��ͬ�޶��տ�����'s ����(ԭ��)���property 
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
     * Object:��ͬ�޶��տ�����'s ��עproperty 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object: ��ͬ�޶��տ����� 's �������� property 
     */
    public com.kingdee.eas.fdc.basedata.PaymentTypeInfo getPaymentType()
    {
        return (com.kingdee.eas.fdc.basedata.PaymentTypeInfo)get("paymentType");
    }
    public void setPaymentType(com.kingdee.eas.fdc.basedata.PaymentTypeInfo item)
    {
        put("paymentType", item);
    }
    /**
     * Object: ��ͬ�޶��տ����� 's ��ͬ�޶� property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillReviseInfo getContractbillRevise()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillReviseInfo)get("contractbillRevise");
    }
    public void setContractbillRevise(com.kingdee.eas.fdc.contract.ContractBillReviseInfo item)
    {
        put("contractbillRevise", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("10E2D076");
    }
}