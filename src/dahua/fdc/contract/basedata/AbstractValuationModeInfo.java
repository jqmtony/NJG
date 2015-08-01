package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractValuationModeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractValuationModeInfo()
    {
        this("id");
    }
    protected AbstractValuationModeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:计价模式's 启用property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E0A26D92");
    }
}