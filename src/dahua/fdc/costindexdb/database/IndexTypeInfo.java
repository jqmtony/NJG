package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;

public class IndexTypeInfo extends AbstractIndexTypeInfo implements Serializable 
{
    public IndexTypeInfo()
    {
        super();
    }
    protected IndexTypeInfo(String pkField)
    {
        super(pkField);
    }
}