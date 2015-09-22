package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;

public class BaseAndSinglePointInfo extends AbstractBaseAndSinglePointInfo implements Serializable 
{
    public BaseAndSinglePointInfo()
    {
        super();
    }
    protected BaseAndSinglePointInfo(String pkField)
    {
        super(pkField);
    }
}