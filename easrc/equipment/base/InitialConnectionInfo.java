package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;

public class InitialConnectionInfo extends AbstractInitialConnectionInfo implements Serializable 
{
    public InitialConnectionInfo()
    {
        super();
    }
    protected InitialConnectionInfo(String pkField)
    {
        super(pkField);
    }
}