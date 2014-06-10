package com.kingdee.eas.port.pm.projectauditsettlement;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectAuditSettlementInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractProjectAuditSettlementInfo()
    {
        this("id");
    }
    protected AbstractProjectAuditSettlementInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目审计决算 's 项目名称 property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProjectName()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("projectName");
    }
    public void setProjectName(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("projectName", item);
    }
    /**
     * Object:项目审计决算's 项目类型property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectTypeEnum getProjectType()
    {
        return com.kingdee.eas.basedata.assistant.ProjectTypeEnum.getEnum(getInt("projectType"));
    }
    public void setProjectType(com.kingdee.eas.basedata.assistant.ProjectTypeEnum item)
    {
		if (item != null) {
        setInt("projectType", item.getValue());
		}
    }
    /**
     * Object:项目审计决算's 项目负责人初审金额property 
     */
    public java.math.BigDecimal getProjectAmount()
    {
        return getBigDecimal("projectAmount");
    }
    public void setProjectAmount(java.math.BigDecimal item)
    {
        setBigDecimal("projectAmount", item);
    }
    /**
     * Object:项目审计决算's 集团审定金额property 
     */
    public java.math.BigDecimal getGroupAmount()
    {
        return getBigDecimal("groupAmount");
    }
    public void setGroupAmount(java.math.BigDecimal item)
    {
        setBigDecimal("groupAmount", item);
    }
    /**
     * Object:项目审计决算's 是否审计property 
     */
    public boolean isAudit()
    {
        return getBoolean("audit");
    }
    public void setAudit(boolean item)
    {
        setBoolean("audit", item);
    }
    /**
     * Object:项目审计决算's 终审金额property 
     */
    public java.math.BigDecimal getFinalAmount()
    {
        return getBigDecimal("finalAmount");
    }
    public void setFinalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("finalAmount", item);
    }
    /**
     * Object:项目审计决算's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: 项目审计决算 's 项目单位 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getEnterprise()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("enterprise");
    }
    public void setEnterprise(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("enterprise", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0016A052");
    }
}