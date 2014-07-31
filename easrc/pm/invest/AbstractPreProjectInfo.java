package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPreProjectInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractPreProjectInfo()
    {
        this("id");
    }
    protected AbstractPreProjectInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.pm.invest.PreProjectE1Collection());
    }
    /**
     * Object: 项目前期 's 模板名称 property 
     */
    public com.kingdee.eas.port.pm.invest.PreProjectTempInfo getTempName()
    {
        return (com.kingdee.eas.port.pm.invest.PreProjectTempInfo)get("tempName");
    }
    public void setTempName(com.kingdee.eas.port.pm.invest.PreProjectTempInfo item)
    {
        put("tempName", item);
    }
    /**
     * Object: 项目前期 's 项目名称 property 
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
     * Object: 项目前期 's 项目前期 property 
     */
    public com.kingdee.eas.port.pm.invest.PreProjectE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.invest.PreProjectE1Collection)get("E1");
    }
    /**
     * Object: 项目前期 's 人员 property 
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
     * Object: 项目前期 's 部门 property 
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
     * Object: 项目前期 's 项目类型 property 
     */
    public com.kingdee.eas.port.pm.base.ProjectTypeInfo getProjectType()
    {
        return (com.kingdee.eas.port.pm.base.ProjectTypeInfo)get("projectType");
    }
    public void setProjectType(com.kingdee.eas.port.pm.base.ProjectTypeInfo item)
    {
        put("projectType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("22677E93");
    }
}