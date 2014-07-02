package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;

public class EquTypeInfo extends AbstractEquTypeInfo implements Serializable 
{
    public EquTypeInfo()
    {
        super();
    }
    protected EquTypeInfo(String pkField)
    {
        super(pkField);
    }
}