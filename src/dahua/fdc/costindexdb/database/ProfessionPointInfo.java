package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;

public class ProfessionPointInfo extends AbstractProfessionPointInfo implements Serializable 
{
    public ProfessionPointInfo()
    {
        super();
    }
    protected ProfessionPointInfo(String pkField)
    {
        super(pkField);
    }
}