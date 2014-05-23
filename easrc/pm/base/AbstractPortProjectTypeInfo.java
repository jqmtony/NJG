package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPortProjectTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractPortProjectTypeInfo()
    {
        this("id");
    }
    protected AbstractPortProjectTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目类型 's 父节点 property 
     */
    public com.kingdee.eas.port.pm.base.PortProjectTypeInfo getParent()
    {
        return (com.kingdee.eas.port.pm.base.PortProjectTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.base.PortProjectTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("41C50451");
    }
}