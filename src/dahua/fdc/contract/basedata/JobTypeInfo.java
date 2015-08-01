package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class JobTypeInfo extends AbstractJobTypeInfo implements Serializable 
{
    public JobTypeInfo()
    {
        super();
    }
    protected JobTypeInfo(String pkField)
    {
        super(pkField);
    }
}