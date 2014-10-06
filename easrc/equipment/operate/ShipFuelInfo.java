package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;

public class ShipFuelInfo extends AbstractShipFuelInfo implements Serializable 
{
    public ShipFuelInfo()
    {
        super();
    }
    protected ShipFuelInfo(String pkField)
    {
        super(pkField);
    }
}