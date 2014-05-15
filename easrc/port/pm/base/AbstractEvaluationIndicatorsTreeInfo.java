package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationIndicatorsTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractEvaluationIndicatorsTreeInfo()
    {
        this("id");
    }
    protected AbstractEvaluationIndicatorsTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评标指标组别 's 父节点 property 
     */
    public com.kingdee.eas.port.pm.base.EvaluationIndicatorsTreeInfo getParent()
    {
        return (com.kingdee.eas.port.pm.base.EvaluationIndicatorsTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.base.EvaluationIndicatorsTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E657019F");
    }
}