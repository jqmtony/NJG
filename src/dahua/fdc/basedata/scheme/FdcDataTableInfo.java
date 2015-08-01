package com.kingdee.eas.fdc.basedata.scheme;

import java.io.Serializable;

public class FdcDataTableInfo extends AbstractFdcDataTableInfo implements Serializable 
{
    public FdcDataTableInfo()
    {
        super();
    }
    protected FdcDataTableInfo(String pkField)
    {
        super(pkField);
    }
}