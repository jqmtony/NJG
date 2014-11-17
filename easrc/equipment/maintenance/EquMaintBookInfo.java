package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;

public class EquMaintBookInfo extends AbstractEquMaintBookInfo implements Serializable 
{
    public EquMaintBookInfo()
    {
        super();
    }
    protected EquMaintBookInfo(String pkField)
    {
        super(pkField);
    }
}