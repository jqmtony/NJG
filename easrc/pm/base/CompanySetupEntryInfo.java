package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class CompanySetupEntryInfo extends AbstractCompanySetupEntryInfo implements Serializable 
{
    public CompanySetupEntryInfo()
    {
        super();
    }
    protected CompanySetupEntryInfo(String pkField)
    {
        super(pkField);
    }
}