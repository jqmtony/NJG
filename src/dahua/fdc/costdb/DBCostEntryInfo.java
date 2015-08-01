package com.kingdee.eas.fdc.costdb;

import java.io.Serializable;

public class DBCostEntryInfo extends AbstractDBCostEntryInfo implements Serializable 
{
    public DBCostEntryInfo()
    {
        super();
    }
    protected DBCostEntryInfo(String pkField)
    {
        super(pkField);
    }
}