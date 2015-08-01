package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PayPlanScheduleTaskBaseInfo extends AbstractPayPlanScheduleTaskBaseInfo implements Serializable 
{
    public PayPlanScheduleTaskBaseInfo()
    {
        super();
    }
    protected PayPlanScheduleTaskBaseInfo(String pkField)
    {
        super(pkField);
    }
}