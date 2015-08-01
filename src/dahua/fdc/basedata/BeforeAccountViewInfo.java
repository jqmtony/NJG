package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class BeforeAccountViewInfo extends AbstractBeforeAccountViewInfo implements Serializable 
{
    public BeforeAccountViewInfo()
    {
        super();
    }
    protected BeforeAccountViewInfo(String pkField)
    {
        super(pkField);
    }
}