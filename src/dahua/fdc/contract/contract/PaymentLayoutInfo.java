package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class PaymentLayoutInfo extends AbstractPaymentLayoutInfo implements Serializable 
{
    public PaymentLayoutInfo()
    {
        super();
    }
    protected PaymentLayoutInfo(String pkField)
    {
        super(pkField);
    }
}