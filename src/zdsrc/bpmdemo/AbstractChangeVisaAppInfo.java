package com.kingdee.eas.bpmdemo;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeVisaAppInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractChangeVisaAppInfo()
    {
        this("id");
    }
    protected AbstractChangeVisaAppInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.bpmdemo.ChangeVisaAppEntryCollection());
        put("SendUnit", new com.kingdee.eas.bpmdemo.ChangeVisaAppSendUnitCollection());
        put("AssEntrys", new com.kingdee.eas.bpmdemo.ChangeVisaAppAssEntryCollection());
    }
    /**
     * Object: ���ǩ֤���� 's �������Ϸ�¼ property 
     */
    public com.kingdee.eas.bpmdemo.ChangeVisaAppEntryCollection getEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ChangeVisaAppEntryCollection)get("entrys");
    }
    /**
     * Object:���ǩ֤����'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object: ���ǩ֤���� 's �������Ϸ�¼ property 
     */
    public com.kingdee.eas.bpmdemo.ChangeVisaAppAssEntryCollection getAssEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ChangeVisaAppAssEntryCollection)get("AssEntrys");
    }
    /**
     * Object: ���ǩ֤���� 's ������� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getConductDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("conductDept");
    }
    public void setConductDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("conductDept", item);
    }
    /**
     * Object: ���ǩ֤���� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurproject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curproject");
    }
    public void setCurproject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curproject", item);
    }
    /**
     * Object:���ǩ֤����'s ��������property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object: ���ǩ֤���� 's ���ԭ�� property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeTypeInfo getChangereason()
    {
        return (com.kingdee.eas.fdc.basedata.ChangeTypeInfo)get("changereason");
    }
    public void setChangereason(com.kingdee.eas.fdc.basedata.ChangeTypeInfo item)
    {
        put("changereason", item);
    }
    /**
     * Object: ���ǩ֤���� 's ������� property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeTypeInfo getAudittype()
    {
        return (com.kingdee.eas.fdc.basedata.ChangeTypeInfo)get("audittype");
    }
    public void setAudittype(com.kingdee.eas.fdc.basedata.ChangeTypeInfo item)
    {
        put("audittype", item);
    }
    /**
     * Object:���ǩ֤����'s �������property 
     */
    public String getChangesubject()
    {
        return getString("changesubject");
    }
    public void setChangesubject(String item)
    {
        setString("changesubject", item);
    }
    /**
     * Object:���ǩ֤����'s ʩ����λproperty 
     */
    public String getConstrSite()
    {
        return getString("constrSite");
    }
    public void setConstrSite(String item)
    {
        setString("constrSite", item);
    }
    /**
     * Object: ���ǩ֤���� 's ����ڼ� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("period", item);
    }
    /**
     * Object: ���ǩ֤���� 's �а����� property 
     */
    public com.kingdee.eas.basedata.org.JobTypeInfo getJobtype()
    {
        return (com.kingdee.eas.basedata.org.JobTypeInfo)get("jobtype");
    }
    public void setJobtype(com.kingdee.eas.basedata.org.JobTypeInfo item)
    {
        put("jobtype", item);
    }
    /**
     * Object:���ǩ֤����'s �����̶�property 
     */
    public com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum getUrgentDegree()
    {
        return com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum.getEnum(getString("UrgentDegree"));
    }
    public void setUrgentDegree(com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum item)
    {
		if (item != null) {
        setString("UrgentDegree", item.getValue());
		}
    }
    /**
     * Object: ���ǩ֤���� 's ʩ����λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getConstrunit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("construnit");
    }
    public void setConstrunit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("construnit", item);
    }
    /**
     * Object: ���ǩ֤���� 's רҵ���� property 
     */
    public com.kingdee.eas.hr.base.SpecialtyTypeInfo getSpecialtyType()
    {
        return (com.kingdee.eas.hr.base.SpecialtyTypeInfo)get("specialtyType");
    }
    public void setSpecialtyType(com.kingdee.eas.hr.base.SpecialtyTypeInfo item)
    {
        put("specialtyType", item);
    }
    /**
     * Object: ���ǩ֤���� 's �����λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getConductUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("conductUnit");
    }
    public void setConductUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("conductUnit", item);
    }
    /**
     * Object:���ǩ֤����'s ��֯property 
     */
    public String getOrgunit()
    {
        return getString("orgunit");
    }
    public void setOrgunit(String item)
    {
        setString("orgunit", item);
    }
    /**
     * Object:���ǩ֤����'s �����property 
     */
    public String getOffer()
    {
        return getString("offer");
    }
    public void setOffer(String item)
    {
        setString("offer", item);
    }
    /**
     * Object: ���ǩ֤���� 's ��Ƶ�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getDesignUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("designUnit");
    }
    public void setDesignUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("designUnit", item);
    }
    /**
     * Object: ���ǩ֤���� 's �·���λ��¼ property 
     */
    public com.kingdee.eas.bpmdemo.ChangeVisaAppSendUnitCollection getSendUnit()
    {
        return (com.kingdee.eas.bpmdemo.ChangeVisaAppSendUnitCollection)get("SendUnit");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("178064D2");
    }
}