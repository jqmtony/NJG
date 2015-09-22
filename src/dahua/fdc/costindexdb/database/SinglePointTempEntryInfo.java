package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;

public class SinglePointTempEntryInfo extends AbstractSinglePointTempEntryInfo implements Serializable 
{
    public SinglePointTempEntryInfo()
    {
        super();
    }
    protected SinglePointTempEntryInfo(String pkField)
    {
        super(pkField);
    }
}