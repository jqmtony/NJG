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
     * Object: 变更签证申请 's 基础资料分录 property 
     */
    public com.kingdee.eas.bpmdemo.ChangeVisaAppEntryCollection getEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ChangeVisaAppEntryCollection)get("entrys");
    }
    /**
     * Object:变更签证申请's 是否生成凭证property 
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
     * Object: 变更签证申请 's 辅助资料分录 property 
     */
    public com.kingdee.eas.bpmdemo.ChangeVisaAppAssEntryCollection getAssEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ChangeVisaAppAssEntryCollection)get("AssEntrys");
    }
    /**
     * Object: 变更签证申请 's 提出部门 property 
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
     * Object: 变更签证申请 's 工程项目 property 
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
     * Object:变更签证申请's 单据名称property 
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
     * Object: 变更签证申请 's 变更原因 property 
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
     * Object: 变更签证申请 's 变更类型 property 
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
     * Object:变更签证申请's 变更主题property 
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
     * Object:变更签证申请's 施工部位property 
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
     * Object: 变更签证申请 's 变更期间 property 
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
     * Object: 变更签证申请 's 承包类型 property 
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
     * Object:变更签证申请's 紧急程度property 
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
     * Object: 变更签证申请 's 施工单位 property 
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
     * Object: 变更签证申请 's 专业类型 property 
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
     * Object: 变更签证申请 's 提出单位 property 
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
     * Object:变更签证申请's 组织property 
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
     * Object:变更签证申请's 提出方property 
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
     * Object: 变更签证申请 's 设计单位 property 
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
     * Object: 变更签证申请 's 下发单位分录 property 
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