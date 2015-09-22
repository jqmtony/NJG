package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;

public class BaseAndSinglePointEntryInfo extends AbstractBaseAndSinglePointEntryInfo implements Serializable 
{
    public BaseAndSinglePointEntryInfo()
    {
        super();
    }
    protected BaseAndSinglePointEntryInfo(String pkField)
    {
        super(pkField);
    }
}