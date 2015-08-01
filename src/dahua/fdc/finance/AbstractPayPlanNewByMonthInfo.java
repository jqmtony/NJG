package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanNewByMonthInfo extends com.kingdee.eas.fdc.finance.PayPlanByMonthBaseInfo implements Serializable 
{
    public AbstractPayPlanNewByMonthInfo()
    {
        this("id");
    }
    protected AbstractPayPlanNewByMonthInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����֧�� 's  property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanNewInfo getParent2()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanNewInfo)get("parent2");
    }
    public void setParent2(com.kingdee.eas.fdc.finance.PayPlanNewInfo item)
    {
        put("parent2", item);
    }
    /**
     * Object:����֧��'s ������property 
     */
    public java.math.BigDecimal getPayAmount()
    {
        return getBigDecimal("payAmount");
    }
    public void setPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payAmount", item);
    }
    /**
     * Object:����֧��'s ��ʼ����property 
     */
    public java.util.Date getBeginDate()
    {
        return getDate("beginDate");
    }
    public void setBeginDate(java.util.Date item)
    {
        setDate("beginDate", item);
    }
    /**
     * Object:����֧��'s ��������property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object: ����֧�� 's ��Դģ���¼ property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanTemplateByMonthInfo getTemplateEntry()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanTemplateByMonthInfo)get("templateEntry");
    }
    public void setTemplateEntry(com.kingdee.eas.fdc.finance.PayPlanTemplateByMonthInfo item)
    {
        put("templateEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8205F84B");
    }
}