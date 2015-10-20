package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ChangeSupplierEntryInfo extends AbstractChangeSupplierEntryInfo implements Serializable 
{
    public ChangeSupplierEntryInfo()
    {
        super();
    }
    protected ChangeSupplierEntryInfo(String pkField)
    {
        super(pkField);
    }
}