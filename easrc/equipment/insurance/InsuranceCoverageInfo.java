package com.kingdee.eas.port.equipment.insurance;

import java.io.Serializable;

public class InsuranceCoverageInfo extends AbstractInsuranceCoverageInfo implements Serializable 
{
    public InsuranceCoverageInfo()
    {
        super();
    }
    protected InsuranceCoverageInfo(String pkField)
    {
        super(pkField);
    }
}