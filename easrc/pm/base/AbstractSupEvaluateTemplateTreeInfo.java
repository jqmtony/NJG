package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupEvaluateTemplateTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractSupEvaluateTemplateTreeInfo()
    {
        this("id");
    }
    protected AbstractSupEvaluateTemplateTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 供应商评估模板组别 's 父节点 property 
     */
    public com.kingdee.eas.port.pm.base.SupEvaluateTemplateTreeInfo getParent()
    {
        return (com.kingdee.eas.port.pm.base.SupEvaluateTemplateTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.base.SupEvaluateTemplateTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6F93007E");
    }
}