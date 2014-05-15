package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEqmTypeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractEqmTypeInfo()
    {
        this("id");
    }
    protected AbstractEqmTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 设备类型 's 组别 property 
     */
    public com.kingdee.eas.port.equipment.base.EqmTypeTreeInfo getTreeid()
    {
        return (com.kingdee.eas.port.equipment.base.EqmTypeTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.port.equipment.base.EqmTypeTreeInfo item)
    {
        put("treeid", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("05DCF399");
    }
}