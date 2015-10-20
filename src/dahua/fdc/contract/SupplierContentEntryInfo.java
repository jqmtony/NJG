package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class SupplierContentEntryInfo extends AbstractSupplierContentEntryInfo implements Serializable 
{
    public SupplierContentEntryInfo()
    {
        super();
    }
    protected SupplierContentEntryInfo(String pkField)
    {
        super(pkField);
    }
}