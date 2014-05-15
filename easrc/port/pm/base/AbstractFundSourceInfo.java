package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFundSourceInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractFundSourceInfo()
    {
        this("id");
    }
    protected AbstractFundSourceInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("77CCC241");
    }
}