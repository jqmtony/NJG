package com.kingdee.eas.custom.richinf;

import java.io.Serializable;

public class RichExamedEntryInfo extends AbstractRichExamedEntryInfo implements Serializable 
{
    public RichExamedEntryInfo()
    {
        super();
    }
    protected RichExamedEntryInfo(String pkField)
    {
        super(pkField);
    }
}