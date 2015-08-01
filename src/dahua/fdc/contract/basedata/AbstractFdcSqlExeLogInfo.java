package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFdcSqlExeLogInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractFdcSqlExeLogInfo()
    {
        this("id");
    }
    protected AbstractFdcSqlExeLogInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���ز�SQLִ����־'s ����property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:���ز�SQLִ����־'s ����property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:���ز�SQLִ����־'s ֵproperty 
     */
    public String getValue()
    {
        return getString("value");
    }
    public void setValue(String item)
    {
        setString("value", item);
    }
    /**
     * Object:���ز�SQLִ����־'s �ο���Ϣproperty 
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
        return new BOSObjectType("73C9F94D");
    }
}