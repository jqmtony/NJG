package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractJudgesComfirmInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractJudgesComfirmInfo()
    {
        this("id");
    }
    protected AbstractJudgesComfirmInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.port.pm.invite.JudgesComfirmEntryCollection());
    }
    /**
     * Object:����ר��ȷ��'s �������property 
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
     * Object:����ר��ȷ��'s �б�����property 
     */
    public String getInviteType()
    {
        return getString("inviteType");
    }
    public void setInviteType(String item)
    {
        setString("inviteType", item);
    }
    /**
     * Object:����ר��ȷ��'s ������Ŀproperty 
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
     * Object:����ר��ȷ��'s ������֯property 
     */
    public String getOrgUnit()
    {
        return getString("orgUnit");
    }
    public void setOrgUnit(String item)
    {
        setString("orgUnit", item);
    }
    /**
     * Object: ����ר��ȷ�� 's ���Ʋ��� property 
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
     * Object:����ר��ȷ��'s ��עproperty 
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
     * Object: ����ר��ȷ�� 's ר����Ϣ property 
     */
    public com.kingdee.eas.port.pm.invite.JudgesComfirmEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.pm.invite.JudgesComfirmEntryCollection)get("Entry");
    }
    /**
     * Object: ����ר��ȷ�� 's �������� property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportInfo getPlanName()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportInfo)get("planName");
    }
    public void setPlanName(com.kingdee.eas.port.pm.invite.InviteReportInfo item)
    {
        put("planName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AF146CBA");
    }
}