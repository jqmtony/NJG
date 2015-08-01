package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class TargetTypeInfo extends AbstractTargetTypeInfo implements Serializable 
{
    public TargetTypeInfo()
    {
        super();
    }
    protected TargetTypeInfo(String pkField)
    {
        super(pkField);
    }
}