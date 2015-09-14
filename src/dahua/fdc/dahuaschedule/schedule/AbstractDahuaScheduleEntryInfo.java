package com.kingdee.eas.fdc.dahuaschedule.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDahuaScheduleEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractDahuaScheduleEntryInfo()
    {
        this("id");
    }
    protected AbstractDahuaScheduleEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleInfo getParent()
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 长编码property 
     */
    public String getLongNumber()
    {
        return getString("longNumber");
    }
    public void setLongNumber(String item)
    {
        setString("longNumber", item);
    }
    /**
     * Object:分录's 是否子节点property 
     */
    public boolean isIsLeaf()
    {
        return getBoolean("isLeaf");
    }
    public void setIsLeaf(boolean item)
    {
        setBoolean("isLeaf", item);
    }
    /**
     * Object:分录's 级次property 
     */
    public int getLevel()
    {
        return getInt("level");
    }
    public void setLevel(int item)
    {
        setInt("level", item);
    }
    /**
     * Object:分录's 任务名称property 
     */
    public String getTask()
    {
        return getString("task");
    }
    public void setTask(String item)
    {
        setString("task", item);
    }
    /**
     * Object:分录's 工期property 
     */
    public java.math.BigDecimal getTimes()
    {
        return getBigDecimal("times");
    }
    public void setTimes(java.math.BigDecimal item)
    {
        setBigDecimal("times", item);
    }
    /**
     * Object:分录's 完成进度property 
     */
    public java.math.BigDecimal getCompleteRate()
    {
        return getBigDecimal("completeRate");
    }
    public void setCompleteRate(java.math.BigDecimal item)
    {
        setBigDecimal("completeRate", item);
    }
    /**
     * Object:分录's 计划开始日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:分录's 计划完成日期property 
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
     * Object: 分录 's 责任人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getManager()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("manager");
    }
    public void setManager(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("manager", item);
    }
    /**
     * Object: 分录 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDepartment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("department");
    }
    public void setDepartment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("department", item);
    }
    /**
     * Object:分录's 实际完成日期property 
     */
    public java.util.Date getActCompleteDate()
    {
        return getDate("actCompleteDate");
    }
    public void setActCompleteDate(java.util.Date item)
    {
        setDate("actCompleteDate", item);
    }
    /**
     * Object:分录's 任务编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:分录's 实际开始时间property 
     */
    public java.util.Date getActStartDate()
    {
        return getDate("actStartDate");
    }
    public void setActStartDate(java.util.Date item)
    {
        setDate("actStartDate", item);
    }
    /**
     * Object:分录's 责任人property 
     */
    public String getManagerText()
    {
        return getString("managerText");
    }
    public void setManagerText(String item)
    {
        setString("managerText", item);
    }
    /**
     * Object:分录's 责任部门property 
     */
    public String getDepartmentText()
    {
        return getString("departmentText");
    }
    public void setDepartmentText(String item)
    {
        setString("departmentText", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CC8749DA");
    }
}