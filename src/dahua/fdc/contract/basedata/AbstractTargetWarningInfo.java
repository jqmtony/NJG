package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTargetWarningInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractTargetWarningInfo()
    {
        this("id");
    }
    protected AbstractTargetWarningInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.basedata.TargetWarningEntryCollection());
    }
    /**
     * Object: 移动运营-预警设置 's null property 
     */
    public com.kingdee.eas.fdc.basedata.TargetWarningEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.basedata.TargetWarningEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A6396319");
    }
}