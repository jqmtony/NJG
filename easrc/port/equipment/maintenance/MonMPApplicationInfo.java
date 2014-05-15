package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;

public class MonMPApplicationInfo extends AbstractMonMPApplicationInfo implements Serializable 
{
    public MonMPApplicationInfo()
    {
        super();
    }
    protected MonMPApplicationInfo(String pkField)
    {
        super(pkField);
    }
}