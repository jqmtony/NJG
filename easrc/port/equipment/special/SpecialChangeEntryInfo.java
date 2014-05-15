package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;

public class SpecialChangeEntryInfo extends AbstractSpecialChangeEntryInfo implements Serializable 
{
    public SpecialChangeEntryInfo()
    {
        super();
    }
    protected SpecialChangeEntryInfo(String pkField)
    {
        super(pkField);
    }
}