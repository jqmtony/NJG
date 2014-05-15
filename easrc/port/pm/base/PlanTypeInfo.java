package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class PlanTypeInfo extends AbstractPlanTypeInfo implements Serializable 
{
    public PlanTypeInfo()
    {
        super();
    }
    protected PlanTypeInfo(String pkField)
    {
        super(pkField);
    }
}