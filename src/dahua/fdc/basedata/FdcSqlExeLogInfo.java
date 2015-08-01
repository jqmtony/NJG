package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class FdcSqlExeLogInfo extends AbstractFdcSqlExeLogInfo implements Serializable 
{
    public FdcSqlExeLogInfo()
    {
        super();
    }
    protected FdcSqlExeLogInfo(String pkField)
    {
        super(pkField);
    }
}