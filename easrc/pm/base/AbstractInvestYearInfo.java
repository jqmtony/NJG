package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvestYearInfo extends com.kingdee.eas.xr.xrbase.XRDataBaseInfo implements Serializable 
{
    public AbstractInvestYearInfo()
    {
        this("id");
    }
    protected AbstractInvestYearInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F18E4CD3");
    }
}