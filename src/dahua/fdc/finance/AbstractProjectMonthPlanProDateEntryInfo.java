package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanProDateEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectMonthPlanProDateEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanProDateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:待签合同&&无合同费用付款计划明细分录's 月份property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object:待签合同&&无合同费用付款计划明细分录's 年份property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object: 待签合同&&无合同费用付款计划明细分录 's 预算项目 property 
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
     * Object:待签合同&&无合同费用付款计划明细分录's 计划支付property 
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
     * Object: 待签合同&&无合同费用付款计划明细分录 's 项目年度付款计划分录（合约规划） property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryInfo getHeadEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryInfo)get("headEntry");
    }
    public void setHeadEntry(com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryInfo item)
    {
        put("headEntry", item);
    }
    /**
     * Object:待签合同&&无合同费用付款计划明细分录's 用款说明property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:待签合同&&无合同费用付款计划明细分录's 用款类型property 
     */
    public com.kingdee.eas.fdc.contract.UseTypeEnum getUseType()
    {
        return com.kingdee.eas.fdc.contract.UseTypeEnum.getEnum(getString("useType"));
    }
    public void setUseType(com.kingdee.eas.fdc.contract.UseTypeEnum item)
    {
		if (item != null) {
        setString("useType", item.getValue());
		}
    }
    /**
     * Object: 待签合同&&无合同费用付款计划明细分录 's 付款类型 property 
     */
    public com.kingdee.eas.fdc.basedata.PaymentTypeInfo getPayType()
    {
        return (com.kingdee.eas.fdc.basedata.PaymentTypeInfo)get("payType");
    }
    public void setPayType(com.kingdee.eas.fdc.basedata.PaymentTypeInfo item)
    {
        put("payType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("10563C96");
    }
}