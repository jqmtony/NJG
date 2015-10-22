package com.kingdee.eas.port.pm.qa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectOAInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractProjectOAInfo()
    {
        this("id");
    }
    protected AbstractProjectOAInfo(String pkField)
    {
        super(pkField);
        put("E2", new com.kingdee.eas.port.pm.qa.ProjectOAE2Collection());
        put("E1", new com.kingdee.eas.port.pm.qa.ProjectOAE1Collection());
    }
    /**
     * Object: 项目协同 's 项目名称 property 
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
     * Object: 项目协同 's 提出人 property 
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
     * Object:项目协同's 内容property 
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
     * Object:项目协同's 备注property 
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
     * Object: 项目协同 's 缺陷跟踪 property 
     */
    public com.kingdee.eas.port.pm.qa.ProjectOAE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.qa.ProjectOAE1Collection)get("E1");
    }
    /**
     * Object:项目协同's 协同名称property 
     */
    public String getQuestionName()
    {
        return getString("questionName");
    }
    public void setQuestionName(String item)
    {
        setString("questionName", item);
    }
    /**
     * Object: 项目协同 's 协同类型 property 
     */
    public com.kingdee.eas.port.pm.qa.OATypeInfo getOatype()
    {
        return (com.kingdee.eas.port.pm.qa.OATypeInfo)get("oatype");
    }
    public void setOatype(com.kingdee.eas.port.pm.qa.OATypeInfo item)
    {
        put("oatype", item);
    }
    /**
     * Object: 项目协同 's 关联单据 property 
     */
    public com.kingdee.eas.port.pm.qa.ProjectOAE2Collection getE2()
    {
        return (com.kingdee.eas.port.pm.qa.ProjectOAE2Collection)get("E2");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FD2BEEC9");
    }
}