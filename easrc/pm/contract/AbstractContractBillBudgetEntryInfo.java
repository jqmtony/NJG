package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractBillBudgetEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractBillBudgetEntryInfo()
    {
        this("id");
    }
    protected AbstractContractBillBudgetEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ͬԤ�� 's null property 
     */
    public com.kingdee.eas.port.pm.contract.ContractBillInfo getParent()
    {
        return (com.kingdee.eas.port.pm.contract.ContractBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.contract.ContractBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��ͬԤ�� 's Ԥ����� property 
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
     * Object:��ͬԤ��'s Ԥ������property 
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
     * Object:��ͬԤ��'s Ԥ����property 
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
     * Object:��ͬԤ��'s ����Ԥ��property 
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
     * Object:��ͬԤ��'s ���κ�ͬԤ��property 
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
     * Object:��ͬԤ��'s ʣ��Ԥ��property 
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
     * Object:��ͬԤ��'s ��עproperty 
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
     * Object:��ͬԤ��'s ������property 
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
        return new BOSObjectType("48BC9F14");
    }
}