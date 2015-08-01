package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEASLogTimeInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractEASLogTimeInfo()
    {
        this("id");
    }
    protected AbstractEASLogTimeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:EAS�����ɹ�ʱ��'s ����ʱ��property 
     */
    public java.sql.Timestamp getLogTime()
    {
        return getTimestamp("logTime");
    }
    public void setLogTime(java.sql.Timestamp item)
    {
        setTimestamp("logTime", item);
    }
    /**
     * Object:EAS�����ɹ�ʱ��'s ϵͳproperty 
     */
    public String getSystem()
    {
        return getString("system");
    }
    public void setSystem(String item)
    {
        setString("system", item);
    }
    /**
     * Object:EAS�����ɹ�ʱ��'s �Ƿ�ɹ�property 
     */
    public boolean isIsSuc()
    {
        return getBoolean("isSuc");
    }
    public void setIsSuc(boolean item)
    {
        setBoolean("isSuc", item);
    }
    /**
     * Object:EAS�����ɹ�ʱ��'s ����ԭ��property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C54AD938");
    }
}