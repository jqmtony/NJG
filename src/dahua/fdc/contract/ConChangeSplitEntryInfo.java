package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ConChangeSplitEntryInfo extends AbstractConChangeSplitEntryInfo implements Serializable 
{
    public ConChangeSplitEntryInfo()
    {
        super();
    }
    protected ConChangeSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
}