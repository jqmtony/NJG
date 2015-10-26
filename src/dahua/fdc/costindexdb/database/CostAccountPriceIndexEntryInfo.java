package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;

public class CostAccountPriceIndexEntryInfo extends AbstractCostAccountPriceIndexEntryInfo implements Serializable 
{
    public CostAccountPriceIndexEntryInfo()
    {
        super();
    }
    protected CostAccountPriceIndexEntryInfo(String pkField)
    {
        super(pkField);
    }
}