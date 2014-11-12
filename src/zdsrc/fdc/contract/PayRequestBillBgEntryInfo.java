package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class PayRequestBillBgEntryInfo extends AbstractPayRequestBillBgEntryInfo implements Serializable 
{
    public PayRequestBillBgEntryInfo()
    {
        super();
    }
    protected PayRequestBillBgEntryInfo(String pkField)
    {
        super(pkField);
    }
}