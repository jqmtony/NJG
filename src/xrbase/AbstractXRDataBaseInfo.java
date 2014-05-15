package com.kingdee.eas.xr.xrbase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractXRDataBaseInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractXRDataBaseInfo()
    {
        this("");
    }
    protected AbstractXRDataBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��������ģ��'s �Ƿ�����property 
     */
    public boolean isIsUsed()
    {
        return getBoolean("isUsed");
    }
    public void setIsUsed(boolean item)
    {
        setBoolean("isUsed", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DBF42476");
    }
}