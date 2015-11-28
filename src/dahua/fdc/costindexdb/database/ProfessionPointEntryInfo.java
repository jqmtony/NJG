package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;

public class ProfessionPointEntryInfo extends AbstractProfessionPointEntryInfo implements Serializable 
{
    public ProfessionPointEntryInfo()
    {
        super();
    }
    protected ProfessionPointEntryInfo(String pkField)
    {
        super(pkField);
    }
}