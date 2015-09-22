package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;

public class SinglePointTempInfo extends AbstractSinglePointTempInfo implements Serializable 
{
    public SinglePointTempInfo()
    {
        super();
    }
    protected SinglePointTempInfo(String pkField)
    {
        super(pkField);
    }
}