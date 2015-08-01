package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjCostEntriesInfo extends com.kingdee.eas.fdc.basedata.ApportionInfoBaseInfo implements Serializable 
{
    public AbstractProjCostEntriesInfo()
    {
        this("id");
    }
    protected AbstractProjCostEntriesInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F6CD9B34");
    }
}