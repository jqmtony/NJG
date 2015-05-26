package com.kingdee.eas.custom.richbase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExamTypeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractExamTypeInfo()
    {
        this("id");
    }
    protected AbstractExamTypeInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B021D2EF");
    }
}