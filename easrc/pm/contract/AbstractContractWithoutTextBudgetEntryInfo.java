package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractWithoutTextBudgetEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractWithoutTextBudgetEntryInfo()
    {
        this("id");
    }
    protected AbstractContractWithoutTextBudgetEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���ı���ͬԤ�� 's null property 
     */
    public com.kingdee.eas.port.pm.contract.ContractWithoutTextInfo getParent()
    {
        return (com.kingdee.eas.port.pm.contract.ContractWithoutTextInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.contract.ContractWithoutTextInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ���ı���ͬԤ�� 's Ԥ����� property 
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
     * Object:���ı���ͬԤ��'s Ԥ������property 
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
     * Object:���ı���ͬԤ��'s Ԥ����property 
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
     * Object:���ı���ͬԤ��'s ����Ԥ��property 
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
     * Object:���ı���ͬԤ��'s ʣ��Ԥ��property 
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
     * Object:���ı���ͬԤ��'s ����Ԥ��property 
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
     * Object:���ı���ͬԤ��'s ��עproperty 
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
        return new BOSObjectType("6EE9CDAA");
    }
}