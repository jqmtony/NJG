package com.kingdee.eas.fdc.basedata.scheme;

import java.io.Serializable;

public class FdcColumnInfo extends AbstractFdcColumnInfo implements Serializable 
{
    public FdcColumnInfo()
    {
        super();
    }
    protected FdcColumnInfo(String pkField)
    {
        super(pkField);
    }
}