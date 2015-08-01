package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBackdropColorInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBackdropColorInfo()
    {
        this("id");
    }
    protected AbstractBackdropColorInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:背景色设置's 颜色property 
     */
    public byte[] getColor()
    {
        return (byte[])get("color");
    }
    public void setColor(byte[] item)
    {
        put("color", item);
    }
    /**
     * Object:背景色设置's 级次property 
     */
    public int getLevel()
    {
        return getInt("level");
    }
    public void setLevel(int item)
    {
        setInt("level", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A153DEFB");
    }
}