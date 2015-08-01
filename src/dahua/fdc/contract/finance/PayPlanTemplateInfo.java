package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class PayPlanTemplateInfo extends AbstractPayPlanTemplateInfo implements Serializable 
{
    public PayPlanTemplateInfo()
    {
        super();
    }
    protected PayPlanTemplateInfo(String pkField)
    {
        super(pkField);
    }
}