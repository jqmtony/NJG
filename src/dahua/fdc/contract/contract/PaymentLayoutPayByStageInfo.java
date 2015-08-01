package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class PaymentLayoutPayByStageInfo extends AbstractPaymentLayoutPayByStageInfo implements Serializable 
{
    public PaymentLayoutPayByStageInfo()
    {
        super();
    }
    protected PaymentLayoutPayByStageInfo(String pkField)
    {
        super(pkField);
    }
}