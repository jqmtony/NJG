package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectIndexDataEntryInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractProjectIndexDataEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectIndexDataEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目指标数据 's 单据头 property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 项目指标数据 's 指标类型 property 
     */
    public com.kingdee.eas.fdc.basedata.TargetTypeInfo getTargetType()
    {
        return (com.kingdee.eas.fdc.basedata.TargetTypeInfo)get("targetType");
    }
    public void setTargetType(com.kingdee.eas.fdc.basedata.TargetTypeInfo item)
    {
        put("targetType", item);
    }
    /**
     * Object: 项目指标数据 's 指标 property 
     */
    public com.kingdee.eas.fdc.basedata.ApportionTypeInfo getApportionType()
    {
        return (com.kingdee.eas.fdc.basedata.ApportionTypeInfo)get("apportionType");
    }
    public void setApportionType(com.kingdee.eas.fdc.basedata.ApportionTypeInfo item)
    {
        put("apportionType", item);
    }
    /**
     * Object:项目指标数据's 指标值property 
     */
    public java.math.BigDecimal getIndexValue()
    {
        return getBigDecimal("indexValue");
    }
    public void setIndexValue(java.math.BigDecimal item)
    {
        setBigDecimal("indexValue", item);
    }
    /**
     * Object:项目指标数据's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:项目指标数据's 汇总property 
     */
    public boolean isBySum()
    {
        return getBoolean("bySum");
    }
    public void setBySum(boolean item)
    {
        setBoolean("bySum", item);
    }
    /**
     * Object:项目指标数据's 项目与产品汇总的差值property 
     */
    public java.math.BigDecimal getDifValue()
    {
        return getBigDecimal("difValue");
    }
    public void setDifValue(java.math.BigDecimal item)
    {
        setBigDecimal("difValue", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3DCF312B");
    }
}