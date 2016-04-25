package com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDhScheduleLogInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractDhScheduleLogInfo()
    {
        this("id");
    }
    protected AbstractDhScheduleLogInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:进度日志's 错误日志property 
     */
    public String getErrorMsg()
    {
        return getString("errorMsg");
    }
    public void setErrorMsg(String item)
    {
        setString("errorMsg", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A1CC5F92");
    }
}