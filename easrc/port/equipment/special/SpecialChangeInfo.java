package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;

public class SpecialChangeInfo extends AbstractSpecialChangeInfo implements Serializable 
{
    public SpecialChangeInfo()
    {
        super();
    }
    protected SpecialChangeInfo(String pkField)
    {
        super(pkField);
    }
}