package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class PayRequestBillConfirmEntryInfo extends AbstractPayRequestBillConfirmEntryInfo implements Serializable 
{
    public PayRequestBillConfirmEntryInfo()
    {
        super();
    }
    protected PayRequestBillConfirmEntryInfo(String pkField)
    {
        super(pkField);
    }
}