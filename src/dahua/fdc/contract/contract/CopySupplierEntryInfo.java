package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class CopySupplierEntryInfo extends AbstractCopySupplierEntryInfo implements Serializable 
{
    public CopySupplierEntryInfo()
    {
        super();
    }
    protected CopySupplierEntryInfo(String pkField)
    {
        super(pkField);
    }
}