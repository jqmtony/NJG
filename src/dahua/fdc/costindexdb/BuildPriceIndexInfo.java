package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;

public class BuildPriceIndexInfo extends AbstractBuildPriceIndexInfo implements Serializable 
{
    public BuildPriceIndexInfo()
    {
        super();
    }
    protected BuildPriceIndexInfo(String pkField)
    {
        super(pkField);
    }
}