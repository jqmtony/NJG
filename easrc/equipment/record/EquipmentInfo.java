package com.kingdee.eas.port.equipment.record;

import java.io.Serializable;

public class EquipmentInfo extends AbstractEquipmentInfo implements Serializable 
{
    public EquipmentInfo()
    {
        super();
    }
    protected EquipmentInfo(String pkField)
    {
        super(pkField);
    }
}