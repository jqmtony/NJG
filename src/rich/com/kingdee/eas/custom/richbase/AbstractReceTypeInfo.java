package com.kingdee.eas.custom.richbase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractReceTypeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractReceTypeInfo()
    {
        this("id");
    }
    protected AbstractReceTypeInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0C73A9A5");
    }
}