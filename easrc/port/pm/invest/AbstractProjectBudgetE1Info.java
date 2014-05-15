package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectBudgetE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectBudgetE1Info()
    {
        this("id");
    }
    protected AbstractProjectBudgetE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ĿԤ�� 's null property 
     */
    public com.kingdee.eas.port.pm.invest.ProjectBudgetInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.ProjectBudgetInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.ProjectBudgetInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��ĿԤ��'s Ԥ����Ŀproperty 
     */
    public String getBudgetName()
    {
        return getString("budgetName");
    }
    public void setBudgetName(String item)
    {
        setString("budgetName", item);
    }
    /**
     * Object:��ĿԤ��'s Ԥ����property 
     */
    public java.math.BigDecimal getBudgetAmount()
    {
        return getBigDecimal("budgetAmount");
    }
    public void setBudgetAmount(java.math.BigDecimal item)
    {
        setBigDecimal("budgetAmount", item);
    }
    /**
     * Object:��ĿԤ��'s �ƻ�����ʱ��property 
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
     * Object:��ĿԤ��'s �ƻ��б�ʱ��property 
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
     * Object:��ĿԤ��'s �ƻ�����ʱ��property 
     */
    public java.util.Date getBuildDate()
    {
        return getDate("buildDate");
    }
    public void setBuildDate(java.util.Date item)
    {
        setDate("buildDate", item);
    }
    /**
     * Object:��ĿԤ��'s �깤����property 
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
     * Object:��ĿԤ��'s ��������ʱ��property 
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
     * Object:��ĿԤ��'s �Ƿ����property 
     */
    public boolean isUnitProject()
    {
        return getBoolean("unitProject");
    }
    public void setUnitProject(boolean item)
    {
        setBoolean("unitProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("932B3F4D");
    }
}