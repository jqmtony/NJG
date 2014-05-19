package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractComproductionEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractComproductionEntryInfo()
    {
        this("id");
    }
    protected AbstractComproductionEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.port.equipment.operate.ComproductionInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.operate.ComproductionInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.operate.ComproductionInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��¼'s ��Ŀproperty 
     */
    public String getProject()
    {
        return getString("project");
    }
    public void setProject(String item)
    {
        setString("project", item);
    }
    /**
     * Object:��¼'s ��Ŀ1property 
     */
    public String getProject1()
    {
        return getString("project1");
    }
    public void setProject1(String item)
    {
        setString("project1", item);
    }
    /**
     * Object:��¼'s ����ʵ��property 
     */
    public java.math.BigDecimal getStagePerformance()
    {
        return getBigDecimal("stagePerformance");
    }
    public void setStagePerformance(java.math.BigDecimal item)
    {
        setBigDecimal("stagePerformance", item);
    }
    /**
     * Object:��¼'s װж�����ܺ�property 
     */
    public java.math.BigDecimal getProEnergy()
    {
        return getBigDecimal("proEnergy");
    }
    public void setProEnergy(java.math.BigDecimal item)
    {
        setBigDecimal("proEnergy", item);
    }
    /**
     * Object:��¼'s ���������ܺ�property 
     */
    public java.math.BigDecimal getFzproEnergy()
    {
        return getBigDecimal("fzproEnergy");
    }
    public void setFzproEnergy(java.math.BigDecimal item)
    {
        setBigDecimal("fzproEnergy", item);
    }
    /**
     * Object:��¼'s �����ܺ�property 
     */
    public java.math.BigDecimal getLifeEnergy()
    {
        return getBigDecimal("lifeEnergy");
    }
    public void setLifeEnergy(java.math.BigDecimal item)
    {
        setBigDecimal("lifeEnergy", item);
    }
    /**
     * Object:��¼'s �����ܺ�property 
     */
    public java.math.BigDecimal getOtherEnergy()
    {
        return getBigDecimal("otherEnergy");
    }
    public void setOtherEnergy(java.math.BigDecimal item)
    {
        setBigDecimal("otherEnergy", item);
    }
    /**
     * Object:��¼'s ȥ��ͬ��ʵ��property 
     */
    public java.math.BigDecimal getSamePerformance()
    {
        return getBigDecimal("samePerformance");
    }
    public void setSamePerformance(java.math.BigDecimal item)
    {
        setBigDecimal("samePerformance", item);
    }
    /**
     * Object:��¼'s ͬ������(+/-)property 
     */
    public java.math.BigDecimal getIncreaseDecrease()
    {
        return getBigDecimal("increaseDecrease");
    }
    public void setIncreaseDecrease(java.math.BigDecimal item)
    {
        setBigDecimal("increaseDecrease", item);
    }
    /**
     * Object:��¼'s ������(%)property 
     */
    public java.math.BigDecimal getIncreaseRate()
    {
        return getBigDecimal("increaseRate");
    }
    public void setIncreaseRate(java.math.BigDecimal item)
    {
        setBigDecimal("increaseRate", item);
    }
    /**
     * Object:��¼'s ���ڵ���property 
     */
    public java.math.BigDecimal getPeriodCon()
    {
        return getBigDecimal("periodCon");
    }
    public void setPeriodCon(java.math.BigDecimal item)
    {
        setBigDecimal("periodCon", item);
    }
    /**
     * Object:��¼'s ȥ��ͬ�ڵ���property 
     */
    public java.math.BigDecimal getSamePeriod()
    {
        return getBigDecimal("samePeriod");
    }
    public void setSamePeriod(java.math.BigDecimal item)
    {
        setBigDecimal("samePeriod", item);
    }
    /**
     * Object:��¼'s ������(%)property 
     */
    public java.math.BigDecimal getIncreaseRate1()
    {
        return getBigDecimal("increaseRate1");
    }
    public void setIncreaseRate1(java.math.BigDecimal item)
    {
        setBigDecimal("increaseRate1", item);
    }
    /**
     * Object:��¼'s ͬ�Ƚڳ���(��-����+)property 
     */
    public java.math.BigDecimal getExcessSection()
    {
        return getBigDecimal("excessSection");
    }
    public void setExcessSection(java.math.BigDecimal item)
    {
        setBigDecimal("excessSection", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("62F96457");
    }
}