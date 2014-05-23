package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;

public class EqmIOInfo extends AbstractEqmIOInfo implements Serializable 
{
    public EqmIOInfo()
    {
        super();
    }
    protected EqmIOInfo(String pkField)
    {
        super(pkField);
    }
}