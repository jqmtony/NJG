package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostTypeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractCostTypeInfo()
    {
        this("id");
    }
    protected AbstractCostTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 费用类型 's 组别 property 
     */
    public com.kingdee.eas.port.pm.base.CostTypeTreeInfo getTreeid()
    {
        return (com.kingdee.eas.port.pm.base.CostTypeTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.port.pm.base.CostTypeTreeInfo item)
    {
        put("treeid", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8D5003E8");
    }
}