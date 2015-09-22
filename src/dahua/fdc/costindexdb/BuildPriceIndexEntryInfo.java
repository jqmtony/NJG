package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;

public class BuildPriceIndexEntryInfo extends AbstractBuildPriceIndexEntryInfo implements Serializable 
{
    public BuildPriceIndexEntryInfo()
    {
        super();
    }
    protected BuildPriceIndexEntryInfo(String pkField)
    {
        super(pkField);
    }
}