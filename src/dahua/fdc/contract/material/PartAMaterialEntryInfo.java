package com.kingdee.eas.fdc.material;

import java.io.Serializable;

public class PartAMaterialEntryInfo extends AbstractPartAMaterialEntryInfo implements Serializable 
{
    public PartAMaterialEntryInfo()
    {
        super();
    }
    protected PartAMaterialEntryInfo(String pkField)
    {
        super(pkField);
    }
}