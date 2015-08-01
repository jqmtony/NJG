package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConPayPlanByScheduleInfo extends com.kingdee.eas.fdc.finance.PayPlanByScheduleBaseInfo implements Serializable 
{
    public AbstractConPayPlanByScheduleInfo()
    {
        this("id");
    }
    protected AbstractConPayPlanByScheduleInfo(String pkField)
    {
        super(pkField);
        put("Task", new com.kingdee.eas.fdc.finance.ConPayPlanByScheduleTaskCollection());
        put("Dataz", new com.kingdee.eas.fdc.finance.ConPayPlanByScheduleDatazCollection());
    }
    /**
     * Object: 按节点支付  's null property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanInfo getParent1()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.finance.ConPayPlanInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object:按节点支付 's 节点成本测算金额property 
     */
    public java.math.BigDecimal getTaskMeasureAmount()
    {
        return getBigDecimal("taskMeasureAmount");
    }
    public void setTaskMeasureAmount(java.math.BigDecimal item)
    {
        setBigDecimal("taskMeasureAmount", item);
    }
    /**
     * Object:按节点支付 's 节点成本结算金额property 
     */
    public java.math.BigDecimal getTaskSettleAmount()
    {
        return getBigDecimal("taskSettleAmount");
    }
    public void setTaskSettleAmount(java.math.BigDecimal item)
    {
        setBigDecimal("taskSettleAmount", item);
    }
    /**
     * Object:按节点支付 's 开始日期property 
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
     * Object:按节点支付 's 结束日期property 
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
     * Object:按节点支付 's 计划支付日期property 
     */
    public java.util.Date getPlanPayDate()
    {
        return getDate("planPayDate");
    }
    public void setPlanPayDate(java.util.Date item)
    {
        setDate("planPayDate", item);
    }
    /**
     * Object:按节点支付 's 计划支付金额property 
     */
    public java.math.BigDecimal getPlanPayAmount()
    {
        return getBigDecimal("planPayAmount");
    }
    public void setPlanPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("planPayAmount", item);
    }
    /**
     * Object: 按节点支付  's 节点数据 property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanByScheduleDatazCollection getDataz()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanByScheduleDatazCollection)get("Dataz");
    }
    /**
     * Object: 按节点支付  's 节点任务 property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanByScheduleTaskCollection getTask()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanByScheduleTaskCollection)get("Task");
    }
    /**
     * Object:按节点支付 's 合约规划IDproperty 
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
        return new BOSObjectType("C3D3550C");
    }
}