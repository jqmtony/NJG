package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class UnitDataManagerInfo extends AbstractUnitDataManagerInfo implements Serializable 
{
    public UnitDataManagerInfo()
    {
        super();
    }
    protected UnitDataManagerInfo(String pkField)
    {
        super(pkField);
    }
}