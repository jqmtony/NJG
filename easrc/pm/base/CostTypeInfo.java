package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class CostTypeInfo extends AbstractCostTypeInfo implements Serializable 
{
    public CostTypeInfo()
    {
        super();
    }
    protected CostTypeInfo(String pkField)
    {
        super(pkField);
    }
}