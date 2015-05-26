package com.kingdee.eas.custom.richinf;

import java.io.Serializable;

public class RichCustomWriteOffEntryInfo extends AbstractRichCustomWriteOffEntryInfo implements Serializable 
{
    public RichCustomWriteOffEntryInfo()
    {
        super();
    }
    protected RichCustomWriteOffEntryInfo(String pkField)
    {
        super(pkField);
    }
}