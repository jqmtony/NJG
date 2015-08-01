package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public abstract class FDCBillEntryBaseInfo extends AbstractFDCBillEntryBaseInfo implements Serializable 
{
    public FDCBillEntryBaseInfo()
    {
        super();
    }
    protected FDCBillEntryBaseInfo(String pkField)
    {
        super(pkField);
    }
}