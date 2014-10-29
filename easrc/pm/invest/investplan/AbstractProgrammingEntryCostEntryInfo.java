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
     * Object: �ɱ����� 's ��ܺ�Լ property 
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
     * Object: �ɱ����� 's �ɱ���Ŀ property 
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
     * Object:�ɱ�����'s Ͷ���ܽ��property 
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
     * Object:�ɱ�����'s �ۼ�Ͷ�ʣ��������꣩property 
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
     * Object:�ɱ�����'s Ͷ�����property 
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
     * Object:�ɱ�����'s �����Ͷ�ʽ��property 
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
     * Object:�ɱ�����'s ��עproperty 
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
     * Object: �ɱ����� 's Ͷ����� property 
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
     * Object:�ɱ�����'s ������Ŀproperty 
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
     * Object:�ɱ�����'s ��Ŀ����property 
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
     * Object:�ɱ�����'s Ͷ�ʱ���property 
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
     * Object:�ɱ�����'s ���property 
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
     * Object:�ɱ�����'s Ԥ����ñ���property 
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
     * Object:�ɱ�����'s Ԥ���������property 
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
     * Object:�ɱ�����'s �ۼ��б��걨���property 
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
     * Object:�ɱ�����'s �ۼ��б���property 
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
     * Object:�ɱ�����'s �ۼƺ�ͬǩ�����property 
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
     * Object:�ɱ�����'s �ۼ����븶����property 
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
     * Object:�ɱ�����'s �ۼ��Ѹ�����property 
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
     * Object:�ɱ�����'s ���ý��property 
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
     * Object:�ɱ�����'s �ӽڵ�property 
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
     * Object:�ɱ�����'s ��עproperty 
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