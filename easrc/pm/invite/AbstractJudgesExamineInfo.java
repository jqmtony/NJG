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
     * Object: ר���ֳ����˱� 's �б귽�� property 
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
     * Object:ר���ֳ����˱�'s ��Ŀ��λ property 
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
     * Object:ר���ֳ����˱�'s ��Ŀ����property 
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
     * Object: ר���ֳ����˱� 's ������ר������ property 
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
     * Object:ר���ֳ����˱�'s �ֳ��ͼ���Աproperty 
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
     * Object:ר���ֳ����˱�'s ��������property 
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
     * Object: ר���ֳ����˱� 's ����ָ���¼ property 
     */
    public com.kingdee.eas.port.pm.invite.JudgesExamineEntryIndicatorCollection getEntryIndicators()
    {
        return (com.kingdee.eas.port.pm.invite.JudgesExamineEntryIndicatorCollection)get("EntryIndicators");
    }
    /**
     * Object: ר���ֳ����˱� 's �б��ල��Ա property 
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
     * Object:ר���ֳ����˱�'s ������ר������property 
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