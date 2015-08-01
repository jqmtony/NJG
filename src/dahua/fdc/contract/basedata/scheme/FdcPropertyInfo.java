package com.kingdee.eas.fdc.basedata.scheme;

import java.io.Serializable;

public class FdcPropertyInfo extends AbstractFdcPropertyInfo implements Serializable 
{
    public FdcPropertyInfo()
    {
        super();
    }
    protected FdcPropertyInfo(String pkField)
    {
        super(pkField);
    }
}