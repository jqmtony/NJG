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
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s �·�property 
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
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s ���property 
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
     * Object: ��Ŀ�¶ȸ���ƻ�������ϸ��¼ 's Ԥ����Ŀ property 
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
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s �ƻ�֧��property 
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
     * Object: ��Ŀ�¶ȸ���ƻ�������ϸ��¼ 's ��Ŀ��ȸ���ƻ���¼ property 
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
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s �ÿ�˵��property 
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
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s �ϱ����property 
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
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s �˶����property 
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
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s �ÿ�����property 
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
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s �ַ����property 
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
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s ��Ʊproperty 
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
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s ��Ʊproperty 
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
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s �ֽ�֧������֧Ʊ��property 
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
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s ����property 
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
     * Object: ��Ŀ�¶ȸ���ƻ�������ϸ��¼ 's �������� property 
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