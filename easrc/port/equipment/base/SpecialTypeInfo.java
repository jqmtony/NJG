package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;

public class SpecialTypeInfo extends AbstractSpecialTypeInfo implements Serializable 
{
    public SpecialTypeInfo()
    {
        super();
    }
    protected SpecialTypeInfo(String pkField)
    {
        super(pkField);
    }
}