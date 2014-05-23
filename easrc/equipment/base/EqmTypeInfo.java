package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;

public class EqmTypeInfo extends AbstractEqmTypeInfo implements Serializable 
{
    public EqmTypeInfo()
    {
        super();
    }
    protected EqmTypeInfo(String pkField)
    {
        super(pkField);
    }
}