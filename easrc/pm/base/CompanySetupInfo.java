package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class CompanySetupInfo extends AbstractCompanySetupInfo implements Serializable 
{
    public CompanySetupInfo()
    {
        super();
    }
    protected CompanySetupInfo(String pkField)
    {
        super(pkField);
    }
}