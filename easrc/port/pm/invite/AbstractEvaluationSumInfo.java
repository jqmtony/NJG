package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationSumInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractEvaluationSumInfo()
    {
        this("id");
    }
    protected AbstractEvaluationSumInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.port.pm.invite.EvaluationSumEntryCollection());
    }
    /**
     * Object: ���������� 's �б귽�� property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportInfo getInviteReport()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportInfo)get("inviteReport");
    }
    public void setInviteReport(com.kingdee.eas.port.pm.invite.InviteReportInfo item)
    {
        put("inviteReport", item);
    }
    /**
     * Object:����������'s �б���Ŀ����property 
     */
    public String getInviteName()
    {
        return getString("inviteName");
    }
    public void setInviteName(String item)
    {
        setString("inviteName", item);
    }
    /**
     * Object: ���������� 's �б������� property 
     */
    public com.kingdee.eas.port.pm.invite.EvaluationSumEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.pm.invite.EvaluationSumEntryCollection)get("Entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("215D9AC6");
    }
}