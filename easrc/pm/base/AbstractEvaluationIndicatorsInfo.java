package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationIndicatorsInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractEvaluationIndicatorsInfo()
    {
        this("id");
    }
    protected AbstractEvaluationIndicatorsInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评标指标 's 组别 property 
     */
    public com.kingdee.eas.port.pm.base.EvaluationIndicatorsTreeInfo getTreeid()
    {
        return (com.kingdee.eas.port.pm.base.EvaluationIndicatorsTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.port.pm.base.EvaluationIndicatorsTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object:评标指标's 启用property 
     */
    public boolean isUse()
    {
        return getBoolean("use");
    }
    public void setUse(boolean item)
    {
        setBoolean("use", item);
    }
    /**
     * Object:评标指标's 评标指标类型property 
     */
    public String getEvalType()
    {
        return getString("evalType");
    }
    public void setEvalType(String item)
    {
        setString("evalType", item);
    }
    /**
     * Object:评标指标's 指标单位property 
     */
    public String getIndicatorDep()
    {
        return getString("indicatorDep");
    }
    public void setIndicatorDep(String item)
    {
        setString("indicatorDep", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("68C13261");
    }
}