package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanScheduleTaskBaseInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPayPlanScheduleTaskBaseInfo()
    {
        this("");
    }
    protected AbstractPayPlanScheduleTaskBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����滮���Ƚڵ���� 's �ڵ����� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("task");
    }
    public void setTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("task", item);
    }
    /**
     * Object:����滮���Ƚڵ����'s ��ʼ����property 
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
     * Object:����滮���Ƚڵ����'s ��������property 
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
     * Object:����滮���Ƚڵ����'s Դ����IDproperty 
     */
    public String getSrcID()
    {
        return getString("srcID");
    }
    public void setSrcID(String item)
    {
        setString("srcID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B1E16DCF");
    }
}