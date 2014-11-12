package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class PayBgEntryInfo extends AbstractPayBgEntryInfo implements Serializable 
{
    public PayBgEntryInfo()
    {
        super();
    }
    protected PayBgEntryInfo(String pkField)
    {
        super(pkField);
    }
}