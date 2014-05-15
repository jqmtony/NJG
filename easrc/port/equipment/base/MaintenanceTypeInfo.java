package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;

public class MaintenanceTypeInfo extends AbstractMaintenanceTypeInfo implements Serializable 
{
    public MaintenanceTypeInfo()
    {
        super();
    }
    protected MaintenanceTypeInfo(String pkField)
    {
        super(pkField);
    }
}