package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEqmTypeTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractEqmTypeTreeInfo()
    {
        this("id");
    }
    protected AbstractEqmTypeTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 设备类型组别 's 父节点 property 
     */
    public com.kingdee.eas.port.equipment.base.EqmTypeTreeInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.base.EqmTypeTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.base.EqmTypeTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("20BF66D7");
    }
}