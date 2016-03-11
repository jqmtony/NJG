package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractStyleInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractStyleInfo()
    {
        this("id");
    }
    protected AbstractStyleInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F8190F8A");
    }
}