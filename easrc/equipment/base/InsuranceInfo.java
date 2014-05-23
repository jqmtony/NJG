package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;

public class InsuranceInfo extends AbstractInsuranceInfo implements Serializable 
{
    public InsuranceInfo()
    {
        super();
    }
    protected InsuranceInfo(String pkField)
    {
        super(pkField);
    }
}