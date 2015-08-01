package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIndexMemoInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractIndexMemoInfo()
    {
        this("id");
    }
    protected AbstractIndexMemoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:指标说明's 键值property 
     */
    public String getKey()
    {
        return getString("key");
    }
    public void setKey(String item)
    {
        setString("key", item);
    }
    /**
     * Object:指标说明's 说明property 
     */
    public String getMemo()
    {
        return getString("memo");
    }
    public void setMemo(String item)
    {
        setString("memo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("65E4927A");
    }
}