package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class CompensationBillInfo extends AbstractCompensationBillInfo implements Serializable 
{
    public CompensationBillInfo()
    {
        super();
    }
    protected CompensationBillInfo(String pkField)
    {
        super(pkField);
    }
}