package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSpecialCheckItemInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractSpecialCheckItemInfo()
    {
        this("id");
    }
    protected AbstractSpecialCheckItemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 特种设备检测项目 's 父节点 property 
     */
    public com.kingdee.eas.port.equipment.base.SpecialCheckItemInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.base.SpecialCheckItemInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.base.SpecialCheckItemInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 特种设备检测项目 's 设备类别 property 
     */
    public com.kingdee.eas.fi.fa.basedata.FaCatInfo getType()
    {
        return (com.kingdee.eas.fi.fa.basedata.FaCatInfo)get("type");
    }
    public void setType(com.kingdee.eas.fi.fa.basedata.FaCatInfo item)
    {
        put("type", item);
    }
    /**
     * Object:特种设备检测项目's 说明property 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("77774DC4");
    }
}