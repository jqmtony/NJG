package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class CostAccountInfo extends AbstractCostAccountInfo implements Serializable 
{
    public CostAccountInfo()
    {
        super();
    }
    protected CostAccountInfo(String pkField)
    {
        super(pkField);
    }
}