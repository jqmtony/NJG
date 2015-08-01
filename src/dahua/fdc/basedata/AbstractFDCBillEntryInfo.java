package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public abstract class AbstractFDCBillEntryInfo extends com.kingdee.eas.fdc.basedata.FDCBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCBillEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7633B6B8");
    }
}