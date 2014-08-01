package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFaLocationInfo extends com.kingdee.eas.xr.xrbase.XRDataBaseInfo implements Serializable 
{
    public AbstractFaLocationInfo()
    {
        this("id");
    }
    protected AbstractFaLocationInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("054E1D52");
    }
}