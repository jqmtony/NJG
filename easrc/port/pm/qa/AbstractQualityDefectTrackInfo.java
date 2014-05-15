package com.kingdee.eas.port.pm.qa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQualityDefectTrackInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractQualityDefectTrackInfo()
    {
        this("id");
    }
    protected AbstractQualityDefectTrackInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.pm.qa.QualityDefectTrackE1Collection());
    }
    /**
     * Object: 质量缺陷跟踪 's 项目名称 property 
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
     * Object: 质量缺陷跟踪 's 提出人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPersonMake()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("personMake");
    }
    public void setPersonMake(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("personMake", item);
    }
    /**
     * Object: 质量缺陷跟踪 's 落实人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPersonImplement()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("personImplement");
    }
    public void setPersonImplement(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("personImplement", item);
    }
    /**
     * Object: 质量缺陷跟踪 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRespondDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("respondDepart");
    }
    public void setRespondDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("respondDepart", item);
    }
    /**
     * Object: 质量缺陷跟踪 's 问题类型 property 
     */
    public com.kingdee.eas.port.pm.base.QuestionTypeTteeInfo getQuestionType()
    {
        return (com.kingdee.eas.port.pm.base.QuestionTypeTteeInfo)get("questionType");
    }
    public void setQuestionType(com.kingdee.eas.port.pm.base.QuestionTypeTteeInfo item)
    {
        put("questionType", item);
    }
    /**
     * Object: 质量缺陷跟踪 's 问题名称 property 
     */
    public com.kingdee.eas.port.pm.base.QuestionTypeInfo getQuestionName()
    {
        return (com.kingdee.eas.port.pm.base.QuestionTypeInfo)get("questionName");
    }
    public void setQuestionName(com.kingdee.eas.port.pm.base.QuestionTypeInfo item)
    {
        put("questionName", item);
    }
    /**
     * Object:质量缺陷跟踪's 影响情况说明property 
     */
    public String getImpactNote()
    {
        return getString("impactNote");
    }
    public void setImpactNote(String item)
    {
        setString("impactNote", item);
    }
    /**
     * Object:质量缺陷跟踪's 问题说明property 
     */
    public String getQuestionDescription()
    {
        return getString("questionDescription");
    }
    public void setQuestionDescription(String item)
    {
        setString("questionDescription", item);
    }
    /**
     * Object:质量缺陷跟踪's 原因分析property 
     */
    public String getReasonAnalysis()
    {
        return getString("reasonAnalysis");
    }
    public void setReasonAnalysis(String item)
    {
        setString("reasonAnalysis", item);
    }
    /**
     * Object:质量缺陷跟踪's 解决方案property 
     */
    public String getSolution()
    {
        return getString("solution");
    }
    public void setSolution(String item)
    {
        setString("solution", item);
    }
    /**
     * Object:质量缺陷跟踪's 经济损失property 
     */
    public java.math.BigDecimal getEconomicLoss()
    {
        return getBigDecimal("economicLoss");
    }
    public void setEconomicLoss(java.math.BigDecimal item)
    {
        setBigDecimal("economicLoss", item);
    }
    /**
     * Object: 质量缺陷跟踪 's 缺陷跟踪 property 
     */
    public com.kingdee.eas.port.pm.qa.QualityDefectTrackE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.qa.QualityDefectTrackE1Collection)get("E1");
    }
    /**
     * Object: 质量缺陷跟踪 's 合同 property 
     */
    public com.kingdee.eas.port.pm.invest.CostTempInfo getContract()
    {
        return (com.kingdee.eas.port.pm.invest.CostTempInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.port.pm.invest.CostTempInfo item)
    {
        put("contract", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9463DD7D");
    }
}