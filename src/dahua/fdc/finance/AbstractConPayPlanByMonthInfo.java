package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConPayPlanByMonthInfo extends com.kingdee.eas.fdc.finance.PayPlanByMonthBaseInfo implements Serializable 
{
    public AbstractConPayPlanByMonthInfo()
    {
        this("id");
    }
    protected AbstractConPayPlanByMonthInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 按月度支付 's  property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanInfo getParent2()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanInfo)get("parent2");
    }
    public void setParent2(com.kingdee.eas.fdc.finance.ConPayPlanInfo item)
    {
        put("parent2", item);
    }
    /**
     * Object:按月度支付's 付款金额property 
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
     * Object:按月度支付's 开始日期property 
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
     * Object:按月度支付's 结束日期property 
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
     * Object:按月度支付's 合约规划IDproperty 
     */
    public String getProgrammingID()
    {
        return getString("programmingID");
    }
    public void setProgrammingID(String item)
    {
        setString("programmingID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("91CA7C2B");
    }
}