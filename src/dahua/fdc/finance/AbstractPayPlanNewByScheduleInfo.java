package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanNewByScheduleInfo extends com.kingdee.eas.fdc.finance.PayPlanByScheduleBaseInfo implements Serializable 
{
    public AbstractPayPlanNewByScheduleInfo()
    {
        this("id");
    }
    protected AbstractPayPlanNewByScheduleInfo(String pkField)
    {
        super(pkField);
        put("Task", new com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskCollection());
        put("Dataz", new com.kingdee.eas.fdc.finance.PayPlanNewByScheduleDatazCollection());
        put("TaskName", new com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskNameCollection());
    }
    /**
     * Object: ���ڵ�֧�� 's  property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanNewInfo getParent1()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanNewInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.finance.PayPlanNewInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object:���ڵ�֧��'s �ڵ�ɱ�������property 
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
     * Object:���ڵ�֧��'s ��ʼ����property 
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
     * Object:���ڵ�֧��'s ��������property 
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
     * Object:���ڵ�֧��'s �ƻ�֧������property 
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
     * Object:���ڵ�֧��'s �ƻ�֧�����property 
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
     * Object: ���ڵ�֧�� 's ��Դģ���¼ property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanTemplateByScheduleInfo getTemplateEntry()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanTemplateByScheduleInfo)get("templateEntry");
    }
    public void setTemplateEntry(com.kingdee.eas.fdc.finance.PayPlanTemplateByScheduleInfo item)
    {
        put("templateEntry", item);
    }
    /**
     * Object: ���ڵ�֧�� 's �ڵ�֧������ property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanNewByScheduleDatazCollection getDataz()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanNewByScheduleDatazCollection)get("Dataz");
    }
    /**
     * Object: ���ڵ�֧�� 's ����ڵ� property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskCollection getTask()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskCollection)get("Task");
    }
    /**
     * Object: ���ڵ�֧�� 's �ڵ����� property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskNameCollection getTaskName()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanNewByScheduleTaskNameCollection)get("TaskName");
    }
    /**
     * Object: ���ڵ�֧�� 's ��Դ�������� property 
     */
    public com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleEntryInfo getScheduleTask()
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleEntryInfo)get("scheduleTask");
    }
    public void setScheduleTask(com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleEntryInfo item)
    {
        put("scheduleTask", item);
    }
    /**
     * Object:���ڵ�֧��'s ֧���ڵ�property 
     */
    public String getScheduleName()
    {
        return getString("scheduleName");
    }
    public void setScheduleName(String item)
    {
        setString("scheduleName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DE24E4EC");
    }
}