package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class SpecialtyTypeEntryInfo extends AbstractSpecialtyTypeEntryInfo implements Serializable 
{
    public SpecialtyTypeEntryInfo()
    {
        super();
    }
    protected SpecialtyTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
}