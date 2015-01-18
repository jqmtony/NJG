package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectStartRequestInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractProjectStartRequestInfo()
    {
        this("id");
    }
    protected AbstractProjectStartRequestInfo(String pkField)
    {
        super(pkField);
        put("E2", new com.kingdee.eas.port.pm.invest.ProjectStartRequestE2Collection());
        put("Entry", new com.kingdee.eas.port.pm.invest.ProjectStartRequestEntryCollection());
    }
    /**
     * Object:项目启动申请's 实施方案property 
     */
    public String getScheme()
    {
        return getString("scheme");
    }
    public void setScheme(String item)
    {
        setString("scheme", item);
    }
    /**
     * Object: 项目启动申请 's 项目名称 property 
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
     * Object: 项目启动申请 's 人员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    /**
     * Object: 项目启动申请 's 部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDeparment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("deparment");
    }
    public void setDeparment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("deparment", item);
    }
    /**
     * Object:项目启动申请's 项目金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: 项目启动申请 's 投资年度 property 
     */
    public com.kingdee.eas.port.pm.base.InvestYearInfo getYear()
    {
        return (com.kingdee.eas.port.pm.base.InvestYearInfo)get("year");
    }
    public void setYear(com.kingdee.eas.port.pm.base.InvestYearInfo item)
    {
        put("year", item);
    }
    /**
     * Object: 项目启动申请 's 项目类型 property 
     */
    public com.kingdee.eas.port.pm.base.ProjectTypeInfo getProjectType()
    {
        return (com.kingdee.eas.port.pm.base.ProjectTypeInfo)get("projectType");
    }
    public void setProjectType(com.kingdee.eas.port.pm.base.ProjectTypeInfo item)
    {
        put("projectType", item);
    }
    /**
     * Object: 项目启动申请 's 招标计划 property 
     */
    public com.kingdee.eas.port.pm.invest.ProjectStartRequestEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.pm.invest.ProjectStartRequestEntryCollection)get("Entry");
    }
    /**
     * Object: 项目启动申请 's 后评估指标 property 
     */
    public com.kingdee.eas.port.pm.invest.ProjectStartRequestE2Collection getE2()
    {
        return (com.kingdee.eas.port.pm.invest.ProjectStartRequestE2Collection)get("E2");
    }
    /**
     * Object:项目启动申请's 备注property 
     */
    public String getBIMUDF0022()
    {
        return getString("BIMUDF0022");
    }
    public void setBIMUDF0022(String item)
    {
        setString("BIMUDF0022", item);
    }
    /**
     * Object:项目启动申请's 指标说明property 
     */
    public String getDec()
    {
        return getString("dec");
    }
    public void setDec(String item)
    {
        setString("dec", item);
    }
    /**
     * Object:项目启动申请's 无需招标property 
     */
    public boolean isWuxu()
    {
        return getBoolean("wuxu");
    }
    public void setWuxu(boolean item)
    {
        setBoolean("wuxu", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3BA1FA89");
    }
}