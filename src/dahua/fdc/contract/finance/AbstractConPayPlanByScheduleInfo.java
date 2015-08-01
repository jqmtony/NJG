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
     * Object: ���ڵ�֧��  's null property 
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
     * Object:���ڵ�֧�� 's �ڵ�ɱ�������property 
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
     * Object:���ڵ�֧�� 's �ڵ�ɱ�������property 
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
     * Object:���ڵ�֧�� 's ��ʼ����property 
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
     * Object:���ڵ�֧�� 's ��������property 
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
     * Object:���ڵ�֧�� 's �ƻ�֧������property 
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
     * Object:���ڵ�֧�� 's �ƻ�֧�����property 
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
     * Object: ���ڵ�֧��  's �ڵ����� property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanByScheduleDatazCollection getDataz()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanByScheduleDatazCollection)get("Dataz");
    }
    /**
     * Object: ���ڵ�֧��  's �ڵ����� property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanByScheduleTaskCollection getTask()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanByScheduleTaskCollection)get("Task");
    }
    /**
     * Object:���ڵ�֧�� 's ��Լ�滮IDproperty 
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