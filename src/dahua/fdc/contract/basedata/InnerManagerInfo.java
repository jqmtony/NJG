package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class InnerManagerInfo extends AbstractInnerManagerInfo implements Serializable 
{
    public InnerManagerInfo()
    {
        super();
    }
    protected InnerManagerInfo(String pkField)
    {
        super(pkField);
    }
}