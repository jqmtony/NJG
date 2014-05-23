package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWinInviteReportInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractWinInviteReportInfo()
    {
        this("id");
    }
    protected AbstractWinInviteReportInfo(String pkField)
    {
        super(pkField);
        put("Unit", new com.kingdee.eas.port.pm.invite.WinInviteReportUnitCollection());
        put("Judges", new com.kingdee.eas.port.pm.invite.WinInviteReportJudgeCollection());
    }
    /**
     * Object: �б굥λ�˱��� 's �б귽�� property 
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
     * Object:�б굥λ�˱���'s ��Ŀ�ſ�property 
     */
    public String getContent()
    {
        return getString("content");
    }
    public void setContent(String item)
    {
        setString("content", item);
    }
    /**
     * Object: �б굥λ�˱��� 's �б귽ʽ property 
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
     * Object:�б굥λ�˱���'s ����ʱ��property 
     */
    public java.util.Date getInviteDate()
    {
        return getDate("inviteDate");
    }
    public void setInviteDate(java.util.Date item)
    {
        setDate("inviteDate", item);
    }
    /**
     * Object:�б굥λ�˱���'s ����ص�property 
     */
    public String getAddress()
    {
        return getString("address");
    }
    public void setAddress(String item)
    {
        setString("address", item);
    }
    /**
     * Object: �б굥λ�˱��� 's ר����ί property 
     */
    public com.kingdee.eas.port.pm.invite.WinInviteReportJudgeCollection getJudges()
    {
        return (com.kingdee.eas.port.pm.invite.WinInviteReportJudgeCollection)get("Judges");
    }
    /**
     * Object: �б굥λ�˱��� 's Ͷ�굥λ property 
     */
    public com.kingdee.eas.port.pm.invite.WinInviteReportUnitCollection getUnit()
    {
        return (com.kingdee.eas.port.pm.invite.WinInviteReportUnitCollection)get("Unit");
    }
    /**
     * Object:�б굥λ�˱���'s �б�Ԥ���property 
     */
    public java.math.BigDecimal getBudgetAmount()
    {
        return getBigDecimal("budgetAmount");
    }
    public void setBudgetAmount(java.math.BigDecimal item)
    {
        setBigDecimal("budgetAmount", item);
    }
    /**
     * Object:�б굥λ�˱���'s ����취property 
     */
    public com.kingdee.eas.port.pm.invite.judgeSolution getEvaSolution()
    {
        return com.kingdee.eas.port.pm.invite.judgeSolution.getEnum(getString("evaSolution"));
    }
    public void setEvaSolution(com.kingdee.eas.port.pm.invite.judgeSolution item)
    {
		if (item != null) {
        setString("evaSolution", item.getValue());
		}
    }
    /**
     * Object:�б굥λ�˱���'s ��������property 
     */
    public java.util.Date getEvaDate()
    {
        return getDate("evaDate");
    }
    public void setEvaDate(java.util.Date item)
    {
        setDate("evaDate", item);
    }
    /**
     * Object:�б굥λ�˱���'s �б���Ŀ����property 
     */
    public String getInvitePrjName()
    {
        return getString("invitePrjName");
    }
    public void setInvitePrjName(String item)
    {
        setString("invitePrjName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F8A0F730");
    }
}