package com.kingdee.eas.port.pm.invest.investplan;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingEntryCostEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractProgrammingEntryCostEntryInfo()
    {
        this("id");
    }
    protected AbstractProgrammingEntryCostEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 成本构成 's 框架合约 property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo getContract()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo item)
    {
        put("contract", item);
    }
    /**
     * Object: 成本构成 's 成本科目 property 
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
     * Object:成本构成's 投资总金额property 
     */
    public java.math.BigDecimal getGoalCost()
    {
        return getBigDecimal("goalCost");
    }
    public void setGoalCost(java.math.BigDecimal item)
    {
        setBigDecimal("goalCost", item);
    }
    /**
     * Object:成本构成's 累计投资（不含本年）property 
     */
    public java.math.BigDecimal getAssigned()
    {
        return getBigDecimal("assigned");
    }
    public void setAssigned(java.math.BigDecimal item)
    {
        setBigDecimal("assigned", item);
    }
    /**
     * Object:成本构成's 投资余额property 
     */
    public java.math.BigDecimal getAssigning()
    {
        return getBigDecimal("assigning");
    }
    public void setAssigning(java.math.BigDecimal item)
    {
        setBigDecimal("assigning", item);
    }
    /**
     * Object:成本构成's 本年度投资金额property 
     */
    public java.math.BigDecimal getContractAssign()
    {
        return getBigDecimal("contractAssign");
    }
    public void setContractAssign(java.math.BigDecimal item)
    {
        setBigDecimal("contractAssign", item);
    }
    /**
     * Object:成本构成's 备注property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: 成本构成 's 投资年度 property 
     */
    public com.kingdee.eas.port.pm.base.InvestYearInfo getInvestYear()
    {
        return (com.kingdee.eas.port.pm.base.InvestYearInfo)get("investYear");
    }
    public void setInvestYear(com.kingdee.eas.port.pm.base.InvestYearInfo item)
    {
        put("investYear", item);
    }
    /**
     * Object:成本构成's 工程项目property 
     */
    public String getProject()
    {
        return getString("project");
    }
    public void setProject(String item)
    {
        setString("project", item);
    }
    /**
     * Object:成本构成's 项目编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:成本构成's 投资比例property 
     */
    public java.math.BigDecimal getProportion()
    {
        return getBigDecimal("proportion");
    }
    public void setProportion(java.math.BigDecimal item)
    {
        setBigDecimal("proportion", item);
    }
    /**
     * Object:成本构成's 年度property 
     */
    public String getYear()
    {
        return getString("year");
    }
    public void setYear(String item)
    {
        setString("year", item);
    }
    /**
     * Object:成本构成's 预算费用编码property 
     */
    public String getFeeNumber()
    {
        return getString("feeNumber");
    }
    public void setFeeNumber(String item)
    {
        setString("feeNumber", item);
    }
    /**
     * Object:成本构成's 预算费用名称property 
     */
    public String getFeeName()
    {
        return getString("feeName");
    }
    public void setFeeName(String item)
    {
        setString("feeName", item);
    }
    /**
     * Object:成本构成's 累计招标申报金额property 
     */
    public java.math.BigDecimal getInvitReportedAmount()
    {
        return getBigDecimal("invitReportedAmount");
    }
    public void setInvitReportedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("invitReportedAmount", item);
    }
    /**
     * Object:成本构成's 累计中标金额property 
     */
    public java.math.BigDecimal getInvitedAmount()
    {
        return getBigDecimal("invitedAmount");
    }
    public void setInvitedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("invitedAmount", item);
    }
    /**
     * Object:成本构成's 累计合同签订金额property 
     */
    public java.math.BigDecimal getContractedAmount()
    {
        return getBigDecimal("contractedAmount");
    }
    public void setContractedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractedAmount", item);
    }
    /**
     * Object:成本构成's 累计申请付款金额property 
     */
    public java.math.BigDecimal getRequestPayAmount()
    {
        return getBigDecimal("requestPayAmount");
    }
    public void setRequestPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("requestPayAmount", item);
    }
    /**
     * Object:成本构成's 累计已付款金额property 
     */
    public java.math.BigDecimal getPayedAmount()
    {
        return getBigDecimal("payedAmount");
    }
    public void setPayedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payedAmount", item);
    }
    /**
     * Object:成本构成's 可用金额property 
     */
    public java.math.BigDecimal getBalanceAmount()
    {
        return getBigDecimal("balanceAmount");
    }
    public void setBalanceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("balanceAmount", item);
    }
    /**
     * Object:成本构成's 子节点property 
     */
    public boolean isIsLast()
    {
        return getBoolean("isLast");
    }
    public void setIsLast(boolean item)
    {
        setBoolean("isLast", item);
    }
    /**
     * Object:成本构成's 备注property 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4F1A141F");
    }
}