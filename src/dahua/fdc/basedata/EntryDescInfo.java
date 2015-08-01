package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class EntryDescInfo extends AbstractEntryDescInfo implements Serializable 
{
    public EntryDescInfo()
    {
        super();
    }
    protected EntryDescInfo(String pkField)
    {
        super(pkField);
    }
}