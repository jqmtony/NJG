package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class CostTargetInfo extends AbstractCostTargetInfo implements Serializable 
{
    public CostTargetInfo()
    {
        super();
    }
    protected CostTargetInfo(String pkField)
    {
        super(pkField);
    }
}