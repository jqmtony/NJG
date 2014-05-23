package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractYearInvestPlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractYearInvestPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractYearInvestPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �̶��ʲ�Ͷ�� 's null property 
     */
    public com.kingdee.eas.port.pm.invest.YearInvestPlanInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.YearInvestPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.YearInvestPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�̶��ʲ�Ͷ��'s ��������property 
     */
    public String getCostName()
    {
        return getString("costName");
    }
    public void setCostName(String item)
    {
        setString("costName", item);
    }
    /**
     * Object:�̶��ʲ�Ͷ��'s ������property 
     */
    public java.math.BigDecimal getEstimate()
    {
        return getBigDecimal("estimate");
    }
    public void setEstimate(java.math.BigDecimal item)
    {
        setBigDecimal("estimate", item);
    }
    /**
     * Object:�̶��ʲ�Ͷ��'s �����Ͷ��Ԥ��property 
     */
    public java.math.BigDecimal getYearInvestBudget()
    {
        return getBigDecimal("yearInvestBudget");
    }
    public void setYearInvestBudget(java.math.BigDecimal item)
    {
        setBigDecimal("yearInvestBudget", item);
    }
    /**
     * Object:�̶��ʲ�Ͷ��'s �ƻ�����ʱ��property 
     */
    public java.util.Date getPlanStartT()
    {
        return getDate("planStartT");
    }
    public void setPlanStartT(java.util.Date item)
    {
        setDate("planStartT", item);
    }
    /**
     * Object:�̶��ʲ�Ͷ��'s �ƻ�����ʱ��property 
     */
    public java.util.Date getAcceptTime()
    {
        return getDate("acceptTime");
    }
    public void setAcceptTime(java.util.Date item)
    {
        setDate("acceptTime", item);
    }
    /**
     * Object:�̶��ʲ�Ͷ��'s ˵��property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: �̶��ʲ�Ͷ�� 's �������� property 
     */
    public com.kingdee.eas.port.pm.base.CostTypeTreeInfo getCostType()
    {
        return (com.kingdee.eas.port.pm.base.CostTypeTreeInfo)get("costType");
    }
    public void setCostType(com.kingdee.eas.port.pm.base.CostTypeTreeInfo item)
    {
        put("costType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2960155A");
    }
}