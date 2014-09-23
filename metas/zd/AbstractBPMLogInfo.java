package com.kingdee.eas.bpm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBPMLogInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBPMLogInfo()
    {
        this("id");
    }
    protected AbstractBPMLogInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:BPM接口日志's 日期property 
     */
    public java.util.Date getLogDate()
    {
        return getDate("logDate");
    }
    public void setLogDate(java.util.Date item)
    {
        setDate("logDate", item);
    }
    /**
     * Object:BPM接口日志's 备注property 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("44F49766");
    }
}