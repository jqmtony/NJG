package com.kingdee.eas.port.pm.fi;

import java.io.Serializable;

public class PayRequestBillEntryInfo extends AbstractPayRequestBillEntryInfo implements Serializable 
{
    public PayRequestBillEntryInfo()
    {
        super();
    }
    protected PayRequestBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}