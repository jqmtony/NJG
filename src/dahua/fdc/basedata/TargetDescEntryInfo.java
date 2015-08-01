package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class TargetDescEntryInfo extends AbstractTargetDescEntryInfo implements Serializable 
{
    public TargetDescEntryInfo()
    {
        super();
    }
    protected TargetDescEntryInfo(String pkField)
    {
        super(pkField);
    }
}