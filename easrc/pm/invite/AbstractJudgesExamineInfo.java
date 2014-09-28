package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractJudgesExamineInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractJudgesExamineInfo()
    {
        this("id");
    }
    protected AbstractJudgesExamineInfo(String pkField)
    {
        super(pkField);
        put("EntryIndicators", new com.kingdee.eas.port.pm.invite.JudgesExamineEntryIndicatorCollection());
    }
    /**
     * Object: 专家现场考核表 's 招标方案 property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportInfo getReportName()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportInfo)get("reportName");
    }
    public void setReportName(com.kingdee.eas.port.pm.invite.InviteReportInfo item)
    {
        put("reportName", item);
    }
    /**
     * Object:专家现场考核表's 项目单位 property 
     */
    public String getPrjOrg()
    {
        return getString("prjOrg");
    }
    public void setPrjOrg(String item)
    {
        setString("prjOrg", item);
    }
    /**
     * Object:专家现场考核表's 项目名称property 
     */
    public String getPrjName()
    {
        return getString("prjName");
    }
    public void setPrjName(String item)
    {
        setString("prjName", item);
    }
    /**
     * Object: 专家现场考核表 's 被考核专家姓名 property 
     */
    public com.kingdee.eas.port.pm.base.JudgesInfo getJudgeName()
    {
        return (com.kingdee.eas.port.pm.base.JudgesInfo)get("judgeName");
    }
    public void setJudgeName(com.kingdee.eas.port.pm.base.JudgesInfo item)
    {
        put("judgeName", item);
    }
    /**
     * Object:专家现场考核表's 现场纪检监察员property 
     */
    public String getCheckPerson()
    {
        return getString("checkPerson");
    }
    public void setCheckPerson(String item)
    {
        setString("checkPerson", item);
    }
    /**
     * Object:专家现场考核表's 评标日期property 
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
     * Object: 专家现场考核表 's 考核指标分录 property 
     */
    public com.kingdee.eas.port.pm.invite.JudgesExamineEntryIndicatorCollection getEntryIndicators()
    {
        return (com.kingdee.eas.port.pm.invite.JudgesExamineEntryIndicatorCollection)get("EntryIndicators");
    }
    /**
     * Object: 专家现场考核表 's 招标办监督人员 property 
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
     * Object:专家现场考核表's 被考核专家姓名property 
     */
    public String getPpr()
    {
        return getString("ppr");
    }
    public void setPpr(String item)
    {
        setString("ppr", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("279694DC");
    }
}