package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;

public class CostAccountPriceIndexInfo extends AbstractCostAccountPriceIndexInfo implements Serializable 
{
    public CostAccountPriceIndexInfo()
    {
        super();
    }
    protected CostAccountPriceIndexInfo(String pkField)
    {
        super(pkField);
    }
}