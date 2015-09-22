package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;

public class BaseAndSinglePointEcostInfo extends AbstractBaseAndSinglePointEcostInfo implements Serializable 
{
    public BaseAndSinglePointEcostInfo()
    {
        super();
    }
    protected BaseAndSinglePointEcostInfo(String pkField)
    {
        super(pkField);
    }
}