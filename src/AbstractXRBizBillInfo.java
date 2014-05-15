package com.kingdee.eas.xr;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractXRBizBillInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractXRBizBillInfo()
    {
        this("id");
    }
    protected AbstractXRBizBillInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("765CC124");
    }
}