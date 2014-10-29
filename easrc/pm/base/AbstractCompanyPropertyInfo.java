package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompanyPropertyInfo extends com.kingdee.eas.xr.xrbase.XRDataBaseInfo implements Serializable 
{
    public AbstractCompanyPropertyInfo()
    {
        this("id");
    }
    protected AbstractCompanyPropertyInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FE1D3711");
    }
}