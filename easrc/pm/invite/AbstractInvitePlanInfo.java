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
    /**
     * Object: 招标计划 's 所属项目 property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProject()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 招标计划 's 招标方式 property 
     */
    public com.kingdee.eas.port.pm.base.InviteTypeInfo getInviteType()
    {
        return (com.kingdee.eas.port.pm.base.InviteTypeInfo)get("inviteType");
    }
    public void setInviteType(com.kingdee.eas.port.pm.base.InviteTypeInfo item)
    {
        put("inviteType", item);
    }
    /**
     * Object:招标计划's 入围邀请property 
     */
    public boolean isShortlistedInv()
    {
        return getBoolean("ShortlistedInv");
    }
    public void setShortlistedInv(boolean item)
    {
        setBoolean("ShortlistedInv", item);
    }
    /**
     * Object:招标计划's 商务标完成property 
     */
    public java.util.Date getBusinessFinish()
    {
        return getDate("BusinessFinish");
    }
    public void setBusinessFinish(java.util.Date item)
    {
        setDate("BusinessFinish", item);
    }
    /**
     * Object:招标计划's 开标日期property 
     */
    public java.util.Date getOpeningBidTime()
    {
        return getDate("openingBidTime");
    }
    public void setOpeningBidTime(java.util.Date item)
    {
        setDate("openingBidTime", item);
    }
    /**
     * Object:招标计划's 发标日期property 
     */
    public java.util.Date getSendOutTime()
    {
        return getDate("SendOutTime");
    }
    public void setSendOutTime(java.util.Date item)
    {
        setDate("SendOutTime", item);
    }
    /**
     * Object:招标计划's 定标日期property 
     */
    public java.util.Date getScalingTime()
    {
        return getDate("scalingTime");
    }
    public void setScalingTime(java.util.Date item)
    {
        setDate("scalingTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("61F9E3DB");
    }
}