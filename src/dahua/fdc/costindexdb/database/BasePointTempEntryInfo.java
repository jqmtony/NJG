package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;

public class BasePointTempEntryInfo extends AbstractBasePointTempEntryInfo implements Serializable 
{
    public BasePointTempEntryInfo()
    {
        super();
    }
    protected BasePointTempEntryInfo(String pkField)
    {
        super(pkField);
    }
}