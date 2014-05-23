package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectStatusInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractProjectStatusInfo()
    {
        this("id");
    }
    protected AbstractProjectStatusInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EB0CF1CA");
    }
}