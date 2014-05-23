package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractJudgesTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractJudgesTreeInfo()
    {
        this("id");
    }
    protected AbstractJudgesTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 专家库组别 's 父节点 property 
     */
    public com.kingdee.eas.port.pm.base.JudgesTreeInfo getParent()
    {
        return (com.kingdee.eas.port.pm.base.JudgesTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.base.JudgesTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F7BA10FB");
    }
}