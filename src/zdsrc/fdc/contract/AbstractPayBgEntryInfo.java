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
     * Object: 费用清单基类 's 费用类别 property 
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
     * Object: 费用清单基类 's 会计科目 property 
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
     * Object:费用清单基类's 本位币金额property 
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
     * Object:费用清单基类's 原币金额property 
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
     * Object: 费用清单基类 's 预算项目 property 
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
     * Object: 费用清单基类 's 币别 property 
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
     * Object:费用清单基类's 汇率property 
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
     * Object:费用清单基类's 预算余额property 
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
     * Object:费用清单基类's 备注说明property 
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