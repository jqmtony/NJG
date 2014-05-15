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
     * Object: ��Ŀ��Ϣ 's ���ڵ� property 
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
     * Object: ��Ŀ��Ϣ 's ��Ŀ״̬ property 
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
     * Object: ��Ŀ��Ϣ 's ��Ŀ���� property 
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
     * Object:��Ŀ��Ϣ's �б�����property 
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
     * Object:��Ŀ��Ϣ's ��������property 
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
     * Object:��Ŀ��Ϣ's ��������property 
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
     * Object:��Ŀ��Ϣ's �깤����property 
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
     * Object:��Ŀ��Ϣ's ��������property 
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
     * Object:��Ŀ��Ϣ's ����˳��property 
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
     * Object:��Ŀ��Ϣ's ������property 
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
     * Object:��Ŀ��Ϣ's Ԥ����property 
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