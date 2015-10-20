package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPaymentLayoutPlamInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractPaymentLayoutPlamInfo()
    {
        this("id");
    }
    protected AbstractPaymentLayoutPlamInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����ƻ� 's null property 
     */
    public com.kingdee.eas.fdc.contract.PaymentLayoutInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.PaymentLayoutInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.PaymentLayoutInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:����ƻ�'s �ƻ������·�property 
     */
    public String getPlanPayMonth()
    {
        return getString("planPayMonth");
    }
    public void setPlanPayMonth(String item)
    {
        setString("planPayMonth", item);
    }
    /**
     * Object:����ƻ�'s �ƻ��������property 
     */
    public java.math.BigDecimal getPlanPayBili()
    {
        return getBigDecimal("planPayBili");
    }
    public void setPlanPayBili(java.math.BigDecimal item)
    {
        setBigDecimal("planPayBili", item);
    }
    /**
     * Object:����ƻ�'s �ƻ�������property 
     */
    public java.math.BigDecimal getPlanPaymoney()
    {
        return getBigDecimal("planPaymoney");
    }
    public void setPlanPaymoney(java.math.BigDecimal item)
    {
        setBigDecimal("planPaymoney", item);
    }
    /**
     * Object:����ƻ�'s �ۼƼƻ�������property 
     */
    public java.math.BigDecimal getLeiJiMoney()
    {
        return getBigDecimal("leiJiMoney");
    }
    public void setLeiJiMoney(java.math.BigDecimal item)
    {
        setBigDecimal("leiJiMoney", item);
    }
    /**
     * Object:����ƻ�'s �ۼƼƻ��������property 
     */
    public java.math.BigDecimal getLeiJiBili()
    {
        return getBigDecimal("leiJiBili");
    }
    public void setLeiJiBili(java.math.BigDecimal item)
    {
        setBigDecimal("leiJiBili", item);
    }
    /**
     * Object:����ƻ�'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2BF5EF3D");
    }
}