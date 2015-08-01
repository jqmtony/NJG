package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractREAutoRememberInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractREAutoRememberInfo()
    {
        this("id");
    }
    protected AbstractREAutoRememberInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:自动记忆's userIDproperty 
     */
    public String getUserID()
    {
        return getString("userID");
    }
    public void setUserID(String item)
    {
        setString("userID", item);
    }
    /**
     * Object:自动记忆's orgUnitIDproperty 
     */
    public String getOrgUnitID()
    {
        return getString("orgUnitID");
    }
    public void setOrgUnitID(String item)
    {
        setString("orgUnitID", item);
    }
    /**
     * Object:自动记忆's functionproperty 
     */
    public String getFunction()
    {
        return getString("function");
    }
    public void setFunction(String item)
    {
        setString("function", item);
    }
    /**
     * Object:自动记忆's valueproperty 
     */
    public String getValue()
    {
        return getString("value");
    }
    public void setValue(String item)
    {
        setString("value", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2088EE81");
    }
}