package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public abstract class FDCBillEntryInfo extends AbstractFDCBillEntryInfo implements Serializable 
{
    public FDCBillEntryInfo()
    {
        super();
    }
    protected FDCBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}