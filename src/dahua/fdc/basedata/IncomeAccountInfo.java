package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class IncomeAccountInfo extends AbstractIncomeAccountInfo implements Serializable 
{
    public IncomeAccountInfo()
    {
        super();
    }
    protected IncomeAccountInfo(String pkField)
    {
        super(pkField);
    }
}