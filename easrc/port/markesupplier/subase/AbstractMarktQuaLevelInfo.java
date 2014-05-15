package com.kingdee.eas.port.markesupplier.subase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarktQuaLevelInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMarktQuaLevelInfo()
    {
        this("id");
    }
    protected AbstractMarktQuaLevelInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���ʵȼ�'s �Ƿ�����property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    /**
     * Object:���ʵȼ�'s ��ע��property 
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
        return new BOSObjectType("061CC058");
    }
}