package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class GuerdonBillInfo extends AbstractGuerdonBillInfo implements Serializable 
{
    public GuerdonBillInfo()
    {
        super();
    }
    protected GuerdonBillInfo(String pkField)
    {
        super(pkField);
    }
}