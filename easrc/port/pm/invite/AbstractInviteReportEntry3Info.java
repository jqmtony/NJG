package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteReportEntry3Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteReportEntry3Info()
    {
        this("id");
    }
    protected AbstractInviteReportEntry3Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 招标成员分录 's null property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invite.InviteReportInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:招标成员分录's 是否组长property 
     */
    public boolean isIsLeader()
    {
        return getBoolean("isLeader");
    }
    public void setIsLeader(boolean item)
    {
        setBoolean("isLeader", item);
    }
    /**
     * Object:招标成员分录's 备注property 
     */
    public String getComment()
    {
        return getString("comment");
    }
    public void setComment(String item)
    {
        setString("comment", item);
    }
    /**
     * Object: 招标成员分录 's 招标成员 property 
     */
    public com.kingdee.eas.port.pm.base.JudgesInfo getInvitePerson()
    {
        return (com.kingdee.eas.port.pm.base.JudgesInfo)get("invitePerson");
    }
    public void setInvitePerson(com.kingdee.eas.port.pm.base.JudgesInfo item)
    {
        put("invitePerson", item);
    }
    /**
     * Object:招标成员分录's 部门property 
     */
    public String getDepartment()
    {
        return getString("department");
    }
    public void setDepartment(String item)
    {
        setString("department", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("76421707");
    }
}