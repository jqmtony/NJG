package com.kingdee.eas.port.equipment.record;

import java.io.Serializable;

public class TankTechnicalInfo extends AbstractTankTechnicalInfo implements Serializable 
{
    public TankTechnicalInfo()
    {
        super();
    }
    protected TankTechnicalInfo(String pkField)
    {
        super(pkField);
    }
}