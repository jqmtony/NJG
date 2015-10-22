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
     * Object: ��ĿЭͬ 's ��Ŀ���� property 
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
     * Object: ��ĿЭͬ 's ����� property 
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
     * Object:��ĿЭͬ's ����property 
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
     * Object:��ĿЭͬ's ��עproperty 
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
     * Object: ��ĿЭͬ 's ȱ�ݸ��� property 
     */
    public com.kingdee.eas.port.pm.qa.ProjectOAE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.qa.ProjectOAE1Collection)get("E1");
    }
    /**
     * Object:��ĿЭͬ's Эͬ����property 
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
     * Object: ��ĿЭͬ 's Эͬ���� property 
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
     * Object: ��ĿЭͬ 's �������� property 
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