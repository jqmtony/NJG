package com.kingdee.eas.port.pm.project;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPortProjectInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractPortProjectInfo()
    {
        this("id");
    }
    protected AbstractPortProjectInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目信息 's 父节点 property 
     */
    public com.kingdee.eas.port.pm.project.PortProjectInfo getParent()
    {
        return (com.kingdee.eas.port.pm.project.PortProjectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.project.PortProjectInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 项目信息 's 项目状态 property 
     */
    public com.kingdee.eas.port.pm.base.ProjectStatusInfo getProjectStatus()
    {
        return (com.kingdee.eas.port.pm.base.ProjectStatusInfo)get("projectStatus");
    }
    public void setProjectStatus(com.kingdee.eas.port.pm.base.ProjectStatusInfo item)
    {
        put("projectStatus", item);
    }
    /**
     * Object: 项目信息 's 项目类型 property 
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
     * Object:项目信息's 招标日期property 
     */
    public java.util.Date getInviteDate()
    {
        return getDate("inviteDate");
    }
    public void setInviteDate(java.util.Date item)
    {
        setDate("inviteDate", item);
    }
    /**
     * Object:项目信息's 启动日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:项目信息's 开工日期property 
     */
    public java.util.Date getBeginDate()
    {
        return getDate("beginDate");
    }
    public void setBeginDate(java.util.Date item)
    {
        setDate("beginDate", item);
    }
    /**
     * Object:项目信息's 完工日期property 
     */
    public java.util.Date getFinishDate()
    {
        return getDate("finishDate");
    }
    public void setFinishDate(java.util.Date item)
    {
        setDate("finishDate", item);
    }
    /**
     * Object:项目信息's 验收日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:项目信息's 开发顺序property 
     */
    public int getIndex()
    {
        return getInt("index");
    }
    public void setIndex(int item)
    {
        setInt("index", item);
    }
    /**
     * Object:项目信息's 概算金额property 
     */
    public java.math.BigDecimal getEstimatesAmount()
    {
        return getBigDecimal("estimatesAmount");
    }
    public void setEstimatesAmount(java.math.BigDecimal item)
    {
        setBigDecimal("estimatesAmount", item);
    }
    /**
     * Object:项目信息's 预算金额property 
     */
    public java.math.BigDecimal getBudgetAmount()
    {
        return getBigDecimal("budgetAmount");
    }
    public void setBudgetAmount(java.math.BigDecimal item)
    {
        setBigDecimal("budgetAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6752A6CD");
    }
}