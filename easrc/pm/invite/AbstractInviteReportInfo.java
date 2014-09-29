package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteReportInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractInviteReportInfo()
    {
        this("id");
    }
    protected AbstractInviteReportInfo(String pkField)
    {
        super(pkField);
        put("Entry4", new com.kingdee.eas.port.pm.invite.InviteReportEntry4Collection());
        put("E7", new com.kingdee.eas.port.pm.invite.InviteReportE7Collection());
        put("Entry1", new com.kingdee.eas.port.pm.invite.InviteReportEntry1Collection());
        put("Entry5", new com.kingdee.eas.port.pm.invite.InviteReportEntry5Collection());
        put("E6", new com.kingdee.eas.port.pm.invite.InviteReportE6Collection());
        put("Entry3", new com.kingdee.eas.port.pm.invite.InviteReportEntry3Collection());
        put("Entry2", new com.kingdee.eas.port.pm.invite.InviteReportEntry2Collection());
    }
    /**
     * Object: �б귽���걨 's ���赥λ property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDevOrg()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("devOrg");
    }
    public void setDevOrg(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("devOrg", item);
    }
    /**
     * Object: �б귽���걨 's ʹ�õ�λ property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseOrg()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useOrg");
    }
    public void setUseOrg(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useOrg", item);
    }
    /**
     * Object:�б귽���걨's ��עproperty 
     */
    public String getBIMUDF0004()
    {
        return getString("BIMUDF0004");
    }
    public void setBIMUDF0004(String item)
    {
        setString("BIMUDF0004", item);
    }
    /**
     * Object: �б귽���걨 's ��¼1 property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportEntry1Collection getEntry1()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportEntry1Collection)get("Entry1");
    }
    /**
     * Object:�б귽���걨's Ͷ�ʸ���property 
     */
    public String getInviteBudget()
    {
        return getString("inviteBudget");
    }
    public void setInviteBudget(String item)
    {
        setString("inviteBudget", item);
    }
    /**
     * Object:�б귽���걨's �б�ʱ��property 
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
     * Object:�б귽���걨's �⿪��ʱ��property 
     */
    public java.util.Date getOpenDate()
    {
        return getDate("openDate");
    }
    public void setOpenDate(java.util.Date item)
    {
        setDate("openDate", item);
    }
    /**
     * Object:�б귽���걨's Ͷ��ʱ��property 
     */
    public java.util.Date getSubDate()
    {
        return getDate("subDate");
    }
    public void setSubDate(java.util.Date item)
    {
        setDate("subDate", item);
    }
    /**
     * Object: �б귽���걨 's �б��걨����д�� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getApplicant()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("applicant");
    }
    public void setApplicant(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("applicant", item);
    }
    /**
     * Object:�б귽���걨's �б걨���ʱ��property 
     */
    public java.util.Date getAudDate()
    {
        return getDate("audDate");
    }
    public void setAudDate(java.util.Date item)
    {
        setDate("audDate", item);
    }
    /**
     * Object:�б귽���걨's �б��ļ���property 
     */
    public String getPaperFee()
    {
        return getString("paperFee");
    }
    public void setPaperFee(String item)
    {
        setString("paperFee", item);
    }
    /**
     * Object:�б귽���걨's Ͷ�걣֤��property 
     */
    public String getBidBond()
    {
        return getString("bidBond");
    }
    public void setBidBond(String item)
    {
        setString("bidBond", item);
    }
    /**
     * Object: �б귽���걨 's �б귽ʽ property 
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
     * Object:�б귽���걨's ���귽��property 
     */
    public com.kingdee.eas.port.pm.invite.judgeSolution getJudgeSolution()
    {
        return com.kingdee.eas.port.pm.invite.judgeSolution.getEnum(getString("judgeSolution"));
    }
    public void setJudgeSolution(com.kingdee.eas.port.pm.invite.judgeSolution item)
    {
		if (item != null) {
        setString("judgeSolution", item.getValue());
		}
    }
    /**
     * Object: �б귽���걨 's �б굥λ��¼ property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportEntry2Collection getEntry2()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportEntry2Collection)get("Entry2");
    }
    /**
     * Object: �б귽���걨 's �б��Ա��¼ property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportEntry3Collection getEntry3()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportEntry3Collection)get("Entry3");
    }
    /**
     * Object:�б귽���걨's ���̵ص�property 
     */
    public String getProSite()
    {
        return getString("proSite");
    }
    public void setProSite(String item)
    {
        setString("proSite", item);
    }
    /**
     * Object: �б귽���걨 's ����ģ�� property 
     */
    public com.kingdee.eas.port.pm.base.EvaluationTemplateInfo getEvaTemplate()
    {
        return (com.kingdee.eas.port.pm.base.EvaluationTemplateInfo)get("evaTemplate");
    }
    public void setEvaTemplate(com.kingdee.eas.port.pm.base.EvaluationTemplateInfo item)
    {
        put("evaTemplate", item);
    }
    /**
     * Object: �б귽���걨 's �б�Ԥ�������Ϣ property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportEntry4Collection getEntry4()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportEntry4Collection)get("Entry4");
    }
    /**
     * Object: �б귽���걨 's ר�ҹ�����Ϣ property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportEntry5Collection getEntry5()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportEntry5Collection)get("Entry5");
    }
    /**
     * Object:�б귽���걨's �б귽������property 
     */
    public String getReportName()
    {
        return getString("reportName");
    }
    public void setReportName(String item)
    {
        setString("reportName", item);
    }
    /**
     * Object: �б귽���걨 's ���������ģ�� property 
     */
    public com.kingdee.eas.port.pm.base.EvaluationTemplateInfo getValidTemplate()
    {
        return (com.kingdee.eas.port.pm.base.EvaluationTemplateInfo)get("validTemplate");
    }
    public void setValidTemplate(com.kingdee.eas.port.pm.base.EvaluationTemplateInfo item)
    {
        put("validTemplate", item);
    }
    /**
     * Object:�б귽���걨's ȥ���������property 
     */
    public String getRmhigh()
    {
        return getString("rmhigh");
    }
    public void setRmhigh(String item)
    {
        setString("rmhigh", item);
    }
    /**
     * Object:�б귽���걨's ȥ���������property 
     */
    public String getRmlow()
    {
        return getString("rmlow");
    }
    public void setRmlow(String item)
    {
        setString("rmlow", item);
    }
    /**
     * Object:�б귽���걨's �����property 
     */
    public String getBusinessScore()
    {
        return getString("businessScore");
    }
    public void setBusinessScore(String item)
    {
        setString("businessScore", item);
    }
    /**
     * Object:�б귽���걨's ������property 
     */
    public String getTechScore()
    {
        return getString("techScore");
    }
    public void setTechScore(String item)
    {
        setString("techScore", item);
    }
    /**
     * Object:�б귽���걨's ��1%��property 
     */
    public String getReduHigh()
    {
        return getString("reduHigh");
    }
    public void setReduHigh(String item)
    {
        setString("reduHigh", item);
    }
    /**
     * Object:�б귽���걨's ��1%��property 
     */
    public String getReduLow()
    {
        return getString("reduLow");
    }
    public void setReduLow(String item)
    {
        setString("reduLow", item);
    }
    /**
     * Object: �б귽���걨 's ��Ŀ���� property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProName()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("proName");
    }
    public void setProName(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("proName", item);
    }
    /**
     * Object:�б귽���걨's ��������property 
     */
    public String getProjectNumber()
    {
        return getString("projectNumber");
    }
    public void setProjectNumber(String item)
    {
        setString("projectNumber", item);
    }
    /**
     * Object:�б귽���걨's ����Ҫ��property 
     */
    public String getQualificationRequemt()
    {
        return getString("QualificationRequemt");
    }
    public void setQualificationRequemt(String item)
    {
        setString("QualificationRequemt", item);
    }
    /**
     * Object: �б귽���걨 's ����ָ����Ϣ property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportE6Collection getE6()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportE6Collection)get("E6");
    }
    /**
     * Object: �б귽���걨 's ��������� property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportE7Collection getE7()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportE7Collection)get("E7");
    }
    /**
     * Object: �б귽���걨 's �б�ƻ� property 
     */
    public com.kingdee.eas.port.pm.invite.InvitePlanInfo getInvitePlan()
    {
        return (com.kingdee.eas.port.pm.invite.InvitePlanInfo)get("invitePlan");
    }
    public void setInvitePlan(com.kingdee.eas.port.pm.invite.InvitePlanInfo item)
    {
        put("invitePlan", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CE1E4966");
    }
}