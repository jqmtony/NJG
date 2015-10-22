package com.kingdee.eas.port.pm.qa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOATypeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractOATypeInfo()
    {
        this("id");
    }
    protected AbstractOATypeInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("398C90AE");
    }
}