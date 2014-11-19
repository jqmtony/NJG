package com.kingdee.eas.bpm.viewpz;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPzViewInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPzViewInfo()
    {
        this("id");
    }
    protected AbstractPzViewInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:视图配置参数设置's sql视图property 
     */
    public String getSqlView()
    {
        return getString("sqlView");
    }
    public void setSqlView(String item)
    {
        setString("sqlView", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A174A7C7");
    }
}