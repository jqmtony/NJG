package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class ChangeTypeInfo extends AbstractChangeTypeInfo implements Serializable 
{
    public ChangeTypeInfo()
    {
        super();
    }
    protected ChangeTypeInfo(String pkField)
    {
        super(pkField);
    }
}