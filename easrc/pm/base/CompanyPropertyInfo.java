package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class CompanyPropertyInfo extends AbstractCompanyPropertyInfo implements Serializable 
{
    public CompanyPropertyInfo()
    {
        super();
    }
    protected CompanyPropertyInfo(String pkField)
    {
        super(pkField);
    }
}