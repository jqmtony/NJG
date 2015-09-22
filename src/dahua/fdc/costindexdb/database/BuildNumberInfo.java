package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;

public class BuildNumberInfo extends AbstractBuildNumberInfo implements Serializable 
{
    public BuildNumberInfo()
    {
        super();
    }
    protected BuildNumberInfo(String pkField)
    {
        super(pkField);
    }
}