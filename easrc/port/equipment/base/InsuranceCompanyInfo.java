package com.kingdee.eas.port.equipment.base;

import java.io.Serializable;

public class InsuranceCompanyInfo extends AbstractInsuranceCompanyInfo implements Serializable 
{
    public InsuranceCompanyInfo()
    {
        super();
    }
    protected InsuranceCompanyInfo(String pkField)
    {
        super(pkField);
    }
}