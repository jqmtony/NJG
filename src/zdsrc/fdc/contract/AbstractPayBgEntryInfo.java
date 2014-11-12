package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayBgEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPayBgEntryInfo()
    {
        this("id");
    }
    protected AbstractPayBgEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����嵥���� 's ������� property 
     */
    public com.kingdee.eas.cp.bc.ExpenseTypeInfo getExpenseType()
    {
        return (com.kingdee.eas.cp.bc.ExpenseTypeInfo)get("expenseType");
    }
    public void setExpenseType(com.kingdee.eas.cp.bc.ExpenseTypeInfo item)
    {
        put("expenseType", item);
    }
    /**
     * Object: �����嵥���� 's ��ƿ�Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountView()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountView");
    }
    public void setAccountView(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountView", item);
    }
    /**
     * Object:�����嵥����'s ��λ�ҽ��property 
     */
    public java.math.BigDecimal getRequestAmount()
    {
        return getBigDecimal("requestAmount");
    }
    public void setRequestAmount(java.math.BigDecimal item)
    {
        setBigDecimal("requestAmount", item);
    }
    /**
     * Object:�����嵥����'s ԭ�ҽ��property 
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
     * Object: �����嵥���� 's Ԥ����Ŀ property 
     */
    public com.kingdee.eas.ma.budget.BgItemInfo getBgItem()
    {
        return (com.kingdee.eas.ma.budget.BgItemInfo)get("bgItem");
    }
    public void setBgItem(com.kingdee.eas.ma.budget.BgItemInfo item)
    {
        put("bgItem", item);
    }
    /**
     * Object: �����嵥���� 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:�����嵥����'s ����property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:�����嵥����'s Ԥ�����property 
     */
    public java.math.BigDecimal getBgBalance()
    {
        return getBigDecimal("bgBalance");
    }
    public void setBgBalance(java.math.BigDecimal item)
    {
        setBigDecimal("bgBalance", item);
    }
    /**
     * Object:�����嵥����'s ��ע˵��property 
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
        return new BOSObjectType("44A37DE0");
    }
}