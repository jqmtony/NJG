package com.kingdee.eas.port.pm.fi;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayRequestBillAcctPayInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPayRequestBillAcctPayInfo()
    {
        this("id");
    }
    protected AbstractPayRequestBillAcctPayInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:成本科目付款分录's 项目IDproperty 
     */
    public String getProjectId()
    {
        return getString("projectId");
    }
    public void setProjectId(String item)
    {
        setString("projectId", item);
    }
    /**
     * Object:成本科目付款分录's 合同IDproperty 
     */
    public String getContractId()
    {
        return getString("contractId");
    }
    public void setContractId(String item)
    {
        setString("contractId", item);
    }
    /**
     * Object:成本科目付款分录's 本次申请金额property 
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
     * Object: 成本科目付款分录 's 付款申请单 property 
     */
    public com.kingdee.eas.port.pm.fi.PayRequestBillInfo getPayRequestBill()
    {
        return (com.kingdee.eas.port.pm.fi.PayRequestBillInfo)get("payRequestBill");
    }
    public void setPayRequestBill(com.kingdee.eas.port.pm.fi.PayRequestBillInfo item)
    {
        put("payRequestBill", item);
    }
    /**
     * Object: 成本科目付款分录 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object: 成本科目付款分录 's 期间 property 
     */
    public com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo getPeriod()
    {
        return (com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo item)
    {
        put("period", item);
    }
    /**
     * Object:成本科目付款分录's 已请款金额property 
     */
    public java.math.BigDecimal getLstAllAmount()
    {
        return getBigDecimal("lstAllAmount");
    }
    public void setLstAllAmount(java.math.BigDecimal item)
    {
        setBigDecimal("lstAllAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("915BCE58");
    }
}