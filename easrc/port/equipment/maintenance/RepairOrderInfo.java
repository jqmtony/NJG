package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;

public class RepairOrderInfo extends AbstractRepairOrderInfo implements Serializable 
{
    public RepairOrderInfo()
    {
        super();
    }
    protected RepairOrderInfo(String pkField)
    {
        super(pkField);
    }
}