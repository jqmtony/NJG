package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPaymentLayoutInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPaymentLayoutInfo()
    {
        this("id");
    }
    protected AbstractPaymentLayoutInfo(String pkField)
    {
        super(pkField);
        put("PayByStages", new com.kingdee.eas.fdc.contract.PaymentLayoutPayByStageCollection());
        put("Plam", new com.kingdee.eas.fdc.contract.PaymentLayoutPlamCollection());
        put("PlanMingxi", new com.kingdee.eas.fdc.contract.PaymentLayoutPlanMingxiCollection());
    }
    /**
     * Object:�����Լ's �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:�����Լ's ��Լ�滮����property 
     */
    public String getContractPlanNo()
    {
        return getString("ContractPlanNo");
    }
    public void setContractPlanNo(String item)
    {
        setString("ContractPlanNo", item);
    }
    /**
     * Object:�����Լ's ����滮����property 
     */
    public String getPlanName()
    {
        return getString("PlanName");
    }
    public void setPlanName(String item)
    {
        setString("PlanName", item);
    }
    /**
     * Object:�����Լ's ��Լ�滮���property 
     */
    public java.math.BigDecimal getPlanAmount()
    {
        return getBigDecimal("PlanAmount");
    }
    public void setPlanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("PlanAmount", item);
    }
    /**
     * Object:�����Լ's ���޽����property 
     */
    public java.math.BigDecimal getCashRate()
    {
        return getBigDecimal("CashRate");
    }
    public void setCashRate(java.math.BigDecimal item)
    {
        setBigDecimal("CashRate", item);
    }
    /**
     * Object:�����Լ's �Ƿ�������ȸ���property 
     */
    public boolean isProgressPayout()
    {
        return getBoolean("ProgressPayout");
    }
    public void setProgressPayout(boolean item)
    {
        setBoolean("ProgressPayout", item);
    }
    /**
     * Object:�����Լ's ��עproperty 
     */
    public String getRemark()
    {
        return getString("Remark");
    }
    public void setRemark(String item)
    {
        setString("Remark", item);
    }
    /**
     * Object: �����Լ 's ����ƻ���ϸ property 
     */
    public com.kingdee.eas.fdc.contract.PaymentLayoutPlanMingxiCollection getPlanMingxi()
    {
        return (com.kingdee.eas.fdc.contract.PaymentLayoutPlanMingxiCollection)get("PlanMingxi");
    }
    /**
     * Object: �����Լ 's ����ƻ� property 
     */
    public com.kingdee.eas.fdc.contract.PaymentLayoutPlamCollection getPlam()
    {
        return (com.kingdee.eas.fdc.contract.PaymentLayoutPlamCollection)get("Plam");
    }
    /**
     * Object: �����Լ 's ���� property 
     */
    public com.kingdee.eas.fdc.contract.PaymentLayoutPayByStageCollection getPayByStages()
    {
        return (com.kingdee.eas.fdc.contract.PaymentLayoutPayByStageCollection)get("PayByStages");
    }
    /**
     * Object:�����Լ's ����滮����property 
     */
    public java.util.Date getPayPlanDate()
    {
        return getDate("PayPlanDate");
    }
    public void setPayPlanDate(java.util.Date item)
    {
        setDate("PayPlanDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("08C83275");
    }
}