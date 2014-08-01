package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;

public class InspectionEquInfo extends AbstractInspectionEquInfo implements Serializable 
{
    public InspectionEquInfo()
    {
        super();
    }
    protected InspectionEquInfo(String pkField)
    {
        super(pkField);
    }
}