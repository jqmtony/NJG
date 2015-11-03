package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanGatherDateEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectMonthPlanGatherDateEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanGatherDateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 月份property 
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
     * Object:项目月度付款计划汇总明细分录's 年份property 
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
     * Object: 项目月度付款计划汇总明细分录 's 预算项目 property 
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
     * Object:项目月度付款计划汇总明细分录's 计划支付property 
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
     * Object: 项目月度付款计划汇总明细分录 's 项目年度付款计划分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo getHeadEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo)get("headEntry");
    }
    public void setHeadEntry(com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo item)
    {
        put("headEntry", item);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 用款说明property 
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
     * Object:项目月度付款计划汇总明细分录's 上报金额property 
     */
    public java.math.BigDecimal getReportAmount()
    {
        return getBigDecimal("reportAmount");
    }
    public void setReportAmount(java.math.BigDecimal item)
    {
        setBigDecimal("reportAmount", item);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 核定金额property 
     */
    public java.math.BigDecimal getExecuteAmount()
    {
        return getBigDecimal("executeAmount");
    }
    public void setExecuteAmount(java.math.BigDecimal item)
    {
        setBigDecimal("executeAmount", item);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 用款类型property 
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
     * Object:项目月度付款计划汇总明细分录's 抵房金额property 
     */
    public java.math.BigDecimal getHouseAmount()
    {
        return getBigDecimal("houseAmount");
    }
    public void setHouseAmount(java.math.BigDecimal item)
    {
        setBigDecimal("houseAmount", item);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 商票property 
     */
    public java.math.BigDecimal getBusinessTicket()
    {
        return getBigDecimal("businessTicket");
    }
    public void setBusinessTicket(java.math.BigDecimal item)
    {
        setBigDecimal("businessTicket", item);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 期票property 
     */
    public java.math.BigDecimal getPromissoryNote()
    {
        return getBigDecimal("promissoryNote");
    }
    public void setPromissoryNote(java.math.BigDecimal item)
    {
        setBigDecimal("promissoryNote", item);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 现金支付（含支票）property 
     */
    public java.math.BigDecimal getCashPayment()
    {
        return getBigDecimal("cashPayment");
    }
    public void setCashPayment(java.math.BigDecimal item)
    {
        setBigDecimal("cashPayment", item);
    }
    /**
     * Object:项目月度付款计划汇总明细分录's 其他property 
     */
    public java.math.BigDecimal getOtherTx()
    {
        return getBigDecimal("otherTx");
    }
    public void setOtherTx(java.math.BigDecimal item)
    {
        setBigDecimal("otherTx", item);
    }
    /**
     * Object: 项目月度付款计划汇总明细分录 's 付款类型 property 
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
        return new BOSObjectType("84AB89EA");
    }
}