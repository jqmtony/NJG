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
     * Object: 付款规划进度节点基类 's 节点任务 property 
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
     * Object:付款规划进度节点基类's 开始日期property 
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
     * Object:付款规划进度节点基类's 结束日期property 
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
     * Object:付款规划进度节点基类's 源任务IDproperty 
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