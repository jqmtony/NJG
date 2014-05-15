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
     * Object: 中标单位核备表 's 招标方案 property 
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
     * Object:中标单位核备表's 项目概况property 
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
     * Object: 中标单位核备表 's 招标方式 property 
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
     * Object:中标单位核备表's 开标时间property 
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
     * Object:中标单位核备表's 开标地点property 
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
     * Object: 中标单位核备表 's 专家评委 property 
     */
    public com.kingdee.eas.port.pm.invite.WinInviteReportJudgeCollection getJudges()
    {
        return (com.kingdee.eas.port.pm.invite.WinInviteReportJudgeCollection)get("Judges");
    }
    /**
     * Object: 中标单位核备表 's 投标单位 property 
     */
    public com.kingdee.eas.port.pm.invite.WinInviteReportUnitCollection getUnit()
    {
        return (com.kingdee.eas.port.pm.invite.WinInviteReportUnitCollection)get("Unit");
    }
    /**
     * Object:中标单位核备表's 招标预算价property 
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
     * Object:中标单位核备表's 评标办法property 
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
     * Object:中标单位核备表's 评标日期property 
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
     * Object:中标单位核备表's 招标项目名称property 
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