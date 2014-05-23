package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationTemplateEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEvaluationTemplateEntryInfo()
    {
        this("id");
    }
    protected AbstractEvaluationTemplateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 指标分录 's null property 
     */
    public com.kingdee.eas.port.pm.base.EvaluationTemplateInfo getParent()
    {
        return (com.kingdee.eas.port.pm.base.EvaluationTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.base.EvaluationTemplateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 指标分录 's 评标指标类型 property 
     */
    public com.kingdee.eas.port.pm.base.EvaluationIndicatorsInfo getIndicatorType()
    {
        return (com.kingdee.eas.port.pm.base.EvaluationIndicatorsInfo)get("indicatorType");
    }
    public void setIndicatorType(com.kingdee.eas.port.pm.base.EvaluationIndicatorsInfo item)
    {
        put("indicatorType", item);
    }
    /**
     * Object:指标分录's 评标指标名称property 
     */
    public String getIndicatorName()
    {
        return getString("indicatorName");
    }
    public void setIndicatorName(String item)
    {
        setString("indicatorName", item);
    }
    /**
     * Object:指标分录's 权重property 
     */
    public java.math.BigDecimal getWeight()
    {
        return getBigDecimal("weight");
    }
    public void setWeight(java.math.BigDecimal item)
    {
        setBigDecimal("weight", item);
    }
    /**
     * Object:指标分录's 备注property 
     */
    public String getComment()
    {
        return getString("comment");
    }
    public void setComment(String item)
    {
        setString("comment", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("71D148DB");
    }
}