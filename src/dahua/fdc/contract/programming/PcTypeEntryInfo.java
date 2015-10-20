package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;

public class PcTypeEntryInfo extends AbstractPcTypeEntryInfo implements Serializable 
{
    public PcTypeEntryInfo()
    {
        super();
    }
    protected PcTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
}