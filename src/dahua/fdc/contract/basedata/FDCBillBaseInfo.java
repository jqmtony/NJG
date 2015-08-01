package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class FDCBillBaseInfo extends AbstractFDCBillBaseInfo implements Serializable 
{
    public FDCBillBaseInfo()
    {
        super();
    }
    protected FDCBillBaseInfo(String pkField)
    {
        super(pkField);
    }
}