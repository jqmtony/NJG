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
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4F1A141F");
    }
}