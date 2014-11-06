package com.kingdee.eas.port.equipment.record;

import java.io.Serializable;

public class PressurePipingInfo extends AbstractPressurePipingInfo implements Serializable 
{
    public PressurePipingInfo()
    {
        super();
    }
    protected PressurePipingInfo(String pkField)
    {
        super(pkField);
    }
}