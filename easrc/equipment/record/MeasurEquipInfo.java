package com.kingdee.eas.port.equipment.record;

import java.io.Serializable;

public class MeasurEquipInfo extends AbstractMeasurEquipInfo implements Serializable 
{
    public MeasurEquipInfo()
    {
        super();
    }
    protected MeasurEquipInfo(String pkField)
    {
        super(pkField);
    }
}