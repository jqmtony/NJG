package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjProEntrApporDataInfo extends com.kingdee.eas.fdc.basedata.ApportionInfoBaseInfo implements Serializable 
{
    public AbstractProjProEntrApporDataInfo()
    {
        this("id");
    }
    protected AbstractProjProEntrApporDataInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D5703A29");
    }
}