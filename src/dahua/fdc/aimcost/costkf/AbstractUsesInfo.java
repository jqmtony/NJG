package com.kingdee.eas.fdc.aimcost.costkf;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractUsesInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractUsesInfo()
    {
        this("id");
    }
    protected AbstractUsesInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:ÓÃÍ¾'s ±¸×¢property 
     */
    public String getBeiz()
    {
        return getString("beiz");
    }
    public void setBeiz(String item)
    {
        setString("beiz", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D1C5FF95");
    }
}