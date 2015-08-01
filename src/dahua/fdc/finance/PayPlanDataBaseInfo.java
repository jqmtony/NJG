package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PayPlanDataBaseInfo extends AbstractPayPlanDataBaseInfo implements Serializable 
{
    public PayPlanDataBaseInfo()
    {
        super();
    }
    protected PayPlanDataBaseInfo(String pkField)
    {
        super(pkField);
    }
}