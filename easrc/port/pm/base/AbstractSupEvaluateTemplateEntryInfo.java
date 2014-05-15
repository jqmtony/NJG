package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupEvaluateTemplateEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupEvaluateTemplateEntryInfo()
    {
        this("id");
    }
    protected AbstractSupEvaluateTemplateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's null property 
     */
    public com.kingdee.eas.port.pm.base.SupEvaluateTemplateInfo getParent()
    {
        return (com.kingdee.eas.port.pm.base.SupEvaluateTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.base.SupEvaluateTemplateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 分录 's 评标指标 property 
     */
    public com.kingdee.eas.port.pm.base.SupEvaluationIndicatorInfo getSupEvaIndicator()
    {
        return (com.kingdee.eas.port.pm.base.SupEvaluationIndicatorInfo)get("supEvaIndicator");
    }
    public void setSupEvaIndicator(com.kingdee.eas.port.pm.base.SupEvaluationIndicatorInfo item)
    {
        put("supEvaIndicator", item);
    }
    /**
     * Object:分录's 评审维度property 
     */
    public String getDimension()
    {
        return getString("dimension");
    }
    public void setDimension(String item)
    {
        setString("dimension", item);
    }
    /**
     * Object:分录's 满分标准property 
     */
    public String getStandard()
    {
        return getString("standard");
    }
    public void setStandard(String item)
    {
        setString("standard", item);
    }
    /**
     * Object:分录's 指标权重property 
     */
    public java.math.BigDecimal getEvaWeight()
    {
        return getBigDecimal("evaWeight");
    }
    public void setEvaWeight(java.math.BigDecimal item)
    {
        setBigDecimal("evaWeight", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("81F81792");
    }
}