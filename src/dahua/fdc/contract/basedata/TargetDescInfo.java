package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class TargetDescInfo extends AbstractTargetDescInfo implements Serializable 
{
    public TargetDescInfo()
    {
        super();
    }
    protected TargetDescInfo(String pkField)
    {
        super(pkField);
    }
}