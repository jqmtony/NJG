package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractYearInvestPlanInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractYearInvestPlanInfo()
    {
        this("id");
    }
    protected AbstractYearInvestPlanInfo(String pkField)
    {
        super(pkField);
        put("E2", new com.kingdee.eas.port.pm.invest.YearInvestPlanE2Collection());
        put("E3", new com.kingdee.eas.port.pm.invest.YearInvestPlanE3Collection());
        put("Entry", new com.kingdee.eas.port.pm.invest.YearInvestPlanEntryCollection());
    }
    /**
     * Object: 年度投资计划 's 建设性质 property 
     */
    public com.kingdee.eas.port.pm.base.BuildTypeInfo getBuildType()
    {
        return (com.kingdee.eas.port.pm.base.BuildTypeInfo)get("buildType");
    }
    public void setBuildType(com.kingdee.eas.port.pm.base.BuildTypeInfo item)
    {
        put("buildType", item);
    }
    /**
     * Object:年度投资计划's 项目名称property 
     */
    public String getProjectName()
    {
        return getString("projectName");
    }
    public void setProjectName(String item)
    {
        setString("projectName", item);
    }
    /**
     * Object: 年度投资计划 's 项目信息 property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getPortProject()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("portProject");
    }
    public void setPortProject(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("portProject", item);
    }
    /**
     * Object: 年度投资计划 's 资金来源 property 
     */
    public com.kingdee.eas.port.pm.base.FundSourceInfo getFundSource()
    {
        return (com.kingdee.eas.port.pm.base.FundSourceInfo)get("fundSource");
    }
    public void setFundSource(com.kingdee.eas.port.pm.base.FundSourceInfo item)
    {
        put("fundSource", item);
    }
    /**
     * Object:年度投资计划's 计划开工日期property 
     */
    public java.util.Date getPlanStartDate()
    {
        return getDate("planStartDate");
    }
    public void setPlanStartDate(java.util.Date item)
    {
        setDate("planStartDate", item);
    }
    /**
     * Object:年度投资计划's 计划完工日期property 
     */
    public java.util.Date getPlanEndDate()
    {
        return getDate("planEndDate");
    }
    public void setPlanEndDate(java.util.Date item)
    {
        setDate("planEndDate", item);
    }
    /**
     * Object: 年度投资计划 's 申报人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRequestPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("requestPerson");
    }
    public void setRequestPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("requestPerson", item);
    }
    /**
     * Object: 年度投资计划 's 申报部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRequestOrg()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("requestOrg");
    }
    public void setRequestOrg(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("requestOrg", item);
    }
    /**
     * Object: 年度投资计划 's 项目类型 property 
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
     * Object:年度投资计划's 累计投资property 
     */
    public java.math.BigDecimal getAddInvestAmount()
    {
        return getBigDecimal("addInvestAmount");
    }
    public void setAddInvestAmount(java.math.BigDecimal item)
    {
        setBigDecimal("addInvestAmount", item);
    }
    /**
     * Object:年度投资计划's 计划投资金额property 
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
     * Object:年度投资计划's 调整后金额property 
     */
    public java.math.BigDecimal getChancedAmount()
    {
        return getBigDecimal("chancedAmount");
    }
    public void setChancedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("chancedAmount", item);
    }
    /**
     * Object:年度投资计划's 项目建设内容property 
     */
    public String getBIMUDF0027()
    {
        return getString("BIMUDF0027");
    }
    public void setBIMUDF0027(String item)
    {
        setString("BIMUDF0027", item);
    }
    /**
     * Object:年度投资计划's 项目地点property 
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
     * Object:年度投资计划's 计划类型property 
     */
    public com.kingdee.eas.port.pm.base.coms.PlanTypeEnum getPlanType()
    {
        return com.kingdee.eas.port.pm.base.coms.PlanTypeEnum.getEnum(getString("planType"));
    }
    public void setPlanType(com.kingdee.eas.port.pm.base.coms.PlanTypeEnum item)
    {
		if (item != null) {
        setString("planType", item.getValue());
		}
    }
    /**
     * Object:年度投资计划's 投资分析property 
     */
    public String getAnalyse()
    {
        return getString("analyse");
    }
    public void setAnalyse(String item)
    {
        setString("analyse", item);
    }
    /**
     * Object:年度投资计划's 实施方案property 
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
     * Object:年度投资计划's 项目状态property 
     */
    public com.kingdee.eas.port.pm.invest.ObjectStateEnum getObjectState()
    {
        return com.kingdee.eas.port.pm.invest.ObjectStateEnum.getEnum(getString("objectState"));
    }
    public void setObjectState(com.kingdee.eas.port.pm.invest.ObjectStateEnum item)
    {
		if (item != null) {
        setString("objectState", item.getValue());
		}
    }
    /**
     * Object: 年度投资计划 's 费用模板 property 
     */
    public com.kingdee.eas.port.pm.invest.CostTempInfo getCostTemp()
    {
        return (com.kingdee.eas.port.pm.invest.CostTempInfo)get("costTemp");
    }
    public void setCostTemp(com.kingdee.eas.port.pm.invest.CostTempInfo item)
    {
        put("costTemp", item);
    }
    /**
     * Object: 年度投资计划 's 固定资产投资 property 
     */
    public com.kingdee.eas.port.pm.invest.YearInvestPlanEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.pm.invest.YearInvestPlanEntryCollection)get("Entry");
    }
    /**
     * Object: 年度投资计划 's 后评估指标 property 
     */
    public com.kingdee.eas.port.pm.invest.YearInvestPlanE2Collection getE2()
    {
        return (com.kingdee.eas.port.pm.invest.YearInvestPlanE2Collection)get("E2");
    }
    /**
     * Object:年度投资计划's 项目投资金额property 
     */
    public java.math.BigDecimal getInvestAmount()
    {
        return getBigDecimal("investAmount");
    }
    public void setInvestAmount(java.math.BigDecimal item)
    {
        setBigDecimal("investAmount", item);
    }
    /**
     * Object:年度投资计划's 备注property 
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
     * Object: 年度投资计划 's 评审意见 property 
     */
    public com.kingdee.eas.port.pm.invest.YearInvestPlanE3Collection getE3()
    {
        return (com.kingdee.eas.port.pm.invest.YearInvestPlanE3Collection)get("E3");
    }
    /**
     * Object: 年度投资计划 's 投资年度 property 
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
     * Object:年度投资计划's 投资余额property 
     */
    public java.math.BigDecimal getBalance()
    {
        return getBigDecimal("balance");
    }
    public void setBalance(java.math.BigDecimal item)
    {
        setBigDecimal("balance", item);
    }
    /**
     * Object: 年度投资计划 's 公司性质 property 
     */
    public com.kingdee.eas.port.pm.base.CompanyPropertyInfo getCompanyProperty()
    {
        return (com.kingdee.eas.port.pm.base.CompanyPropertyInfo)get("companyProperty");
    }
    public void setCompanyProperty(com.kingdee.eas.port.pm.base.CompanyPropertyInfo item)
    {
        put("companyProperty", item);
    }
    /**
     * Object:年度投资计划's 序号property 
     */
    public java.math.BigDecimal getSeq()
    {
        return getBigDecimal("seq");
    }
    public void setSeq(java.math.BigDecimal item)
    {
        setBigDecimal("seq", item);
    }
    /**
     * Object:年度投资计划's 填写说明property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5047FDF8");
    }
}