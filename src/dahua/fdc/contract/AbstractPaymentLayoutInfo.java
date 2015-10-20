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
     * Object:付款规约's 是否生成凭证property 
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
     * Object:付款规约's 合约规划编码property 
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
     * Object:付款规约's 付款规划名称property 
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
     * Object:付款规约's 合约规划金额property 
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
     * Object:付款规约's 保修金比例property 
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
     * Object:付款规约's 是否按形象进度付款property 
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
     * Object:付款规约's 备注property 
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
     * Object: 付款规约 's 付款计划明细 property 
     */
    public com.kingdee.eas.fdc.contract.PaymentLayoutPlanMingxiCollection getPlanMingxi()
    {
        return (com.kingdee.eas.fdc.contract.PaymentLayoutPlanMingxiCollection)get("PlanMingxi");
    }
    /**
     * Object: 付款规约 's 付款计划 property 
     */
    public com.kingdee.eas.fdc.contract.PaymentLayoutPlamCollection getPlam()
    {
        return (com.kingdee.eas.fdc.contract.PaymentLayoutPlamCollection)get("Plam");
    }
    /**
     * Object: 付款规约 's 分期 property 
     */
    public com.kingdee.eas.fdc.contract.PaymentLayoutPayByStageCollection getPayByStages()
    {
        return (com.kingdee.eas.fdc.contract.PaymentLayoutPayByStageCollection)get("PayByStages");
    }
    /**
     * Object:付款规约's 付款规划日期property 
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