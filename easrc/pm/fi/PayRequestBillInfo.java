package com.kingdee.eas.port.pm.fi;

import java.io.Serializable;

public class PayRequestBillInfo extends AbstractPayRequestBillInfo implements Serializable 
{
    public PayRequestBillInfo()
    {
        super();
    }
    protected PayRequestBillInfo(String pkField)
    {
        super(pkField);
    }
}