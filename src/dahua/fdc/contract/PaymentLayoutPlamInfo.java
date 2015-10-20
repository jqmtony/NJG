package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class PaymentLayoutPlamInfo extends AbstractPaymentLayoutPlamInfo implements Serializable 
{
    public PaymentLayoutPlamInfo()
    {
        super();
    }
    protected PaymentLayoutPlamInfo(String pkField)
    {
        super(pkField);
    }
}