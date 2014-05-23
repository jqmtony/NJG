package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostTypeTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractCostTypeTreeInfo()
    {
        this("id");
    }
    protected AbstractCostTypeTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 费用类型组别 's 父节点 property 
     */
    public com.kingdee.eas.port.pm.base.CostTypeTreeInfo getParent()
    {
        return (com.kingdee.eas.port.pm.base.CostTypeTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.base.CostTypeTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9C83B7A6");
    }
}