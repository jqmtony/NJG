package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPreprojectE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPreprojectE1Info()
    {
        this("id");
    }
    protected AbstractPreprojectE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目前期 's null property 
     */
    public com.kingdee.eas.port.pm.invest.PreprojectInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.PreprojectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.PreprojectInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:项目前期's 前期工作内容property 
     */
    public String getPreWorkContent()
    {
        return getString("preWorkContent");
    }
    public void setPreWorkContent(String item)
    {
        setString("preWorkContent", item);
    }
    /**
     * Object:项目前期's 工作要求property 
     */
    public String getWorkReq()
    {
        return getString("workReq");
    }
    public void setWorkReq(String item)
    {
        setString("workReq", item);
    }
    /**
     * Object: 项目前期 's 责任部门 property 
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
     * Object:项目前期's 计划开始时间property 
     */
    public java.util.Date getPlanStartTime()
    {
        return getDate("planStartTime");
    }
    public void setPlanStartTime(java.util.Date item)
    {
        setDate("planStartTime", item);
    }
    /**
     * Object:项目前期's 计划完成时间property 
     */
    public java.util.Date getPlanCompTime()
    {
        return getDate("planCompTime");
    }
    public void setPlanCompTime(java.util.Date item)
    {
        setDate("planCompTime", item);
    }
    /**
     * Object:项目前期's 实际开始时间property 
     */
    public java.util.Date getActualStartTime()
    {
        return getDate("actualStartTime");
    }
    public void setActualStartTime(java.util.Date item)
    {
        setDate("actualStartTime", item);
    }
    /**
     * Object:项目前期's 实际完成时间property 
     */
    public java.util.Date getActualCompTime()
    {
        return getDate("actualCompTime");
    }
    public void setActualCompTime(java.util.Date item)
    {
        setDate("actualCompTime", item);
    }
    /**
     * Object: 项目前期 's 协办部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getCoDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("coDepart");
    }
    public void setCoDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("coDepart", item);
    }
    /**
     * Object: 项目前期 's 主办人员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getHostPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("hostPerson");
    }
    public void setHostPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("hostPerson", item);
    }
    /**
     * Object:项目前期's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AF100E7F");
    }
}