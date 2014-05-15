package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvitePlanInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractInvitePlanInfo()
    {
        this("id");
    }
    protected AbstractInvitePlanInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:招标计划's 计划编码property 
     */
    public String getPlanNumber()
    {
        return getString("planNumber");
    }
    public void setPlanNumber(String item)
    {
        setString("planNumber", item);
    }
    /**
     * Object:招标计划's 计划名称property 
     */
    public String getPlanName()
    {
        return getString("planName");
    }
    public void setPlanName(String item)
    {
        setString("planName", item);
    }
    /**
     * Object:招标计划's 计划开始日期property 
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
     * Object:招标计划's 计划结束日期property 
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
     * Object:招标计划's 进场日期property 
     */
    public java.util.Date getInDate()
    {
        return getDate("inDate");
    }
    public void setInDate(java.util.Date item)
    {
        setDate("inDate", item);
    }
    /**
     * Object: 招标计划 's 责任人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getResponse()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("response");
    }
    public void setResponse(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("response", item);
    }
    /**
     * Object: 招标计划 's 所属项目 property 
     */
    public com.kingdee.eas.port.pm.project.PortProjectInfo getProject()
    {
        return (com.kingdee.eas.port.pm.project.PortProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.port.pm.project.PortProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 招标计划 's 责任部门 property 
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
     * Object:招标计划's 备注property 
     */
    public String getBIMUDF0009()
    {
        return getString("BIMUDF0009");
    }
    public void setBIMUDF0009(String item)
    {
        setString("BIMUDF0009", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("61F9E3DB");
    }
}