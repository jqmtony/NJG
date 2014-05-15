package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationTemplateTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractEvaluationTemplateTreeInfo()
    {
        this("id");
    }
    protected AbstractEvaluationTemplateTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评标模板组别 's 父节点 property 
     */
    public com.kingdee.eas.port.pm.base.EvaluationTemplateTreeInfo getParent()
    {
        return (com.kingdee.eas.port.pm.base.EvaluationTemplateTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.base.EvaluationTemplateTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A09A0215");
    }
}