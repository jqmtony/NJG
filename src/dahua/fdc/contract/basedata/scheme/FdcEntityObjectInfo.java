package com.kingdee.eas.fdc.basedata.scheme;

import java.io.Serializable;

public class FdcEntityObjectInfo extends AbstractFdcEntityObjectInfo implements Serializable 
{
    public FdcEntityObjectInfo()
    {
        super();
    }
    protected FdcEntityObjectInfo(String pkField)
    {
        super(pkField);
    }
}