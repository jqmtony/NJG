package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTargetTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractTargetTypeInfo()
    {
        this("id");
    }
    protected AbstractTargetTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:ָ������'s ����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: ָ������ 's ����� property 
     */
    public com.kingdee.eas.fdc.basedata.TargetTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.TargetTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.TargetTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("482AD4DD");
    }
}