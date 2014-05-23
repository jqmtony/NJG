package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class FundSourceInfo extends AbstractFundSourceInfo implements Serializable 
{
    public FundSourceInfo()
    {
        super();
    }
    protected FundSourceInfo(String pkField)
    {
        super(pkField);
    }
}