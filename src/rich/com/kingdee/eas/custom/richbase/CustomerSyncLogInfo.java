package com.kingdee.eas.custom.richbase;

import java.io.Serializable;

public class CustomerSyncLogInfo extends AbstractCustomerSyncLogInfo implements Serializable 
{
    public CustomerSyncLogInfo()
    {
        super();
    }
    protected CustomerSyncLogInfo(String pkField)
    {
        super(pkField);
    }
}