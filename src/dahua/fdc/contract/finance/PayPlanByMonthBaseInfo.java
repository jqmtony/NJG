package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PayPlanByMonthBaseInfo extends AbstractPayPlanByMonthBaseInfo implements Serializable 
{
    public PayPlanByMonthBaseInfo()
    {
        super();
    }
    protected PayPlanByMonthBaseInfo(String pkField)
    {
        super(pkField);
    }
}