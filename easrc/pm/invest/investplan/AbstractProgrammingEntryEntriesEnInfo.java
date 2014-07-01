package com.kingdee.eas.port.pm.invest.investplan;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingEntryEntriesEnInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProgrammingEntryEntriesEnInfo()
    {
        this("id");
    }
    protected AbstractProgrammingEntryEntriesEnInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第5个表体 's null property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo getParent1()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object:第5个表体's 工程项目property 
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
     * Object:第5个表体's 项目编码property 
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
     * Object: 第5个表体 's 投资年度 property 
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
     * Object:第5个表体's 投资总金额property 
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
     * Object:第5个表体's 累计投资（不含本年度）property 
     */
    public java.math.BigDecimal getCumulativeInvest()
    {
        return getBigDecimal("cumulativeInvest");
    }
    public void setCumulativeInvest(java.math.BigDecimal item)
    {
        setBigDecimal("cumulativeInvest", item);
    }
    /**
     * Object:第5个表体's 本年度投资金额property 
     */
    public java.math.BigDecimal getInvestAmount()
    {
        return getBigDecimal("investAmount");
    }
    public void setInvestAmount(java.math.BigDecimal item)
    {
        setBigDecimal("investAmount", item);
    }
    /**
     * Object:第5个表体's 投资余额property 
     */
    public java.math.BigDecimal getBalance()
    {
        return getBigDecimal("balance");
    }
    public void setBalance(java.math.BigDecimal item)
    {
        setBigDecimal("balance", item);
    }
    /**
     * Object:第5个表体's 投资比例property 
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
     * Object:第5个表体's 备注property 
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
        return new BOSObjectType("43357CD3");
    }
}