package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWinInviteReportBudgetEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractWinInviteReportBudgetEntryInfo()
    {
        this("id");
    }
    protected AbstractWinInviteReportBudgetEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �б�Ԥ�� 's null property 
     */
    public com.kingdee.eas.port.pm.invite.WinInviteReportInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invite.WinInviteReportInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invite.WinInviteReportInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �б�Ԥ�� 's Ԥ����� property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo getBudgetNumber()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo)get("budgetNumber");
    }
    public void setBudgetNumber(com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo item)
    {
        put("budgetNumber", item);
    }
    /**
     * Object:�б�Ԥ��'s Ԥ������property 
     */
    public String getBudgetName()
    {
        return getString("budgetName");
    }
    public void setBudgetName(String item)
    {
        setString("budgetName", item);
    }
    /**
     * Object:�б�Ԥ��'s Ԥ����property 
     */
    public java.math.BigDecimal getBudgetAmount()
    {
        return getBigDecimal("budgetAmount");
    }
    public void setBudgetAmount(java.math.BigDecimal item)
    {
        setBigDecimal("budgetAmount", item);
    }
    /**
     * Object:�б�Ԥ��'s ����Ԥ��property 
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
     * Object:�б�Ԥ��'s �����б���property 
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
     * Object:�б�Ԥ��'s Ԥ�����property 
     */
    public java.math.BigDecimal getLastAmount()
    {
        return getBigDecimal("lastAmount");
    }
    public void setLastAmount(java.math.BigDecimal item)
    {
        setBigDecimal("lastAmount", item);
    }
    /**
     * Object:�б�Ԥ��'s ��עproperty 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    /**
     * Object:�б�Ԥ��'s ������property 
     */
    public java.math.BigDecimal getDiffAmount()
    {
        return getBigDecimal("diffAmount");
    }
    public void setDiffAmount(java.math.BigDecimal item)
    {
        setBigDecimal("diffAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A2E07EDD");
    }
}