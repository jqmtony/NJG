package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ConChangeTempInfo extends AbstractConChangeTempInfo implements Serializable 
{
    public ConChangeTempInfo()
    {
        super();
    }
    protected ConChangeTempInfo(String pkField)
    {
        super(pkField);
    }
}