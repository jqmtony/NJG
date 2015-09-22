package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;

public class BasePointTempInfo extends AbstractBasePointTempInfo implements Serializable 
{
    public BasePointTempInfo()
    {
        super();
    }
    protected BasePointTempInfo(String pkField)
    {
        super(pkField);
    }
}