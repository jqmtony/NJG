package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;

public class EquLeaseInfo extends AbstractEquLeaseInfo implements Serializable 
{
    public EquLeaseInfo()
    {
        super();
    }
    protected EquLeaseInfo(String pkField)
    {
        super(pkField);
    }
}