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
     * Object: ��Ŀǰ�� 's ģ������ property 
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
     * Object: ��Ŀǰ�� 's ��Ŀ���� property 
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
     * Object: ��Ŀǰ�� 's ��Ŀǰ�� property 
     */
    public com.kingdee.eas.port.pm.invest.PreProjectE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.invest.PreProjectE1Collection)get("E1");
    }
    /**
     * Object: ��Ŀǰ�� 's ��Ա property 
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
     * Object: ��Ŀǰ�� 's ���� property 
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
     * Object: ��Ŀǰ�� 's ��Ŀ���� property 
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