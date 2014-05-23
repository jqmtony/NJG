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
     * Object: ����ȱ�ݸ��� 's ��Ŀ���� property 
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
     * Object: ����ȱ�ݸ��� 's ����� property 
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
     * Object: ����ȱ�ݸ��� 's ��ʵ�� property 
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
     * Object: ����ȱ�ݸ��� 's ���β��� property 
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
     * Object: ����ȱ�ݸ��� 's �������� property 
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
     * Object: ����ȱ�ݸ��� 's �������� property 
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
     * Object:����ȱ�ݸ���'s Ӱ�����˵��property 
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
     * Object:����ȱ�ݸ���'s ����˵��property 
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
     * Object:����ȱ�ݸ���'s ԭ�����property 
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
     * Object:����ȱ�ݸ���'s �������property 
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
     * Object:����ȱ�ݸ���'s ������ʧproperty 
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
     * Object: ����ȱ�ݸ��� 's ȱ�ݸ��� property 
     */
    public com.kingdee.eas.port.pm.qa.QualityDefectTrackE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.qa.QualityDefectTrackE1Collection)get("E1");
    }
    /**
     * Object: ����ȱ�ݸ��� 's ��ͬ property 
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