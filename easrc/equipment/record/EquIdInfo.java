package com.kingdee.eas.port.equipment.record;

import java.io.Serializable;

public class EquIdInfo extends AbstractEquIdInfo implements Serializable 
{
    public EquIdInfo()
    {
        super();
    }
    protected EquIdInfo(String pkField)
    {
        super(pkField);
    }
}