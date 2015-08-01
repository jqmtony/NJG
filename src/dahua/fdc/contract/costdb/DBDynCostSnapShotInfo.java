package com.kingdee.eas.fdc.costdb;

import java.io.Serializable;

public class DBDynCostSnapShotInfo extends AbstractDBDynCostSnapShotInfo implements Serializable 
{
    public DBDynCostSnapShotInfo()
    {
        super();
    }
    protected DBDynCostSnapShotInfo(String pkField)
    {
        super(pkField);
    }
}