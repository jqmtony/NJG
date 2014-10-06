package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;

public class ShipReportFormInfo extends AbstractShipReportFormInfo implements Serializable 
{
    public ShipReportFormInfo()
    {
        super();
    }
    protected ShipReportFormInfo(String pkField)
    {
        super(pkField);
    }
}