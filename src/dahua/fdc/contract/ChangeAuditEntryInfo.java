package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ChangeAuditEntryInfo extends AbstractChangeAuditEntryInfo implements Serializable 
{
    public ChangeAuditEntryInfo()
    {
        super();
    }
    protected ChangeAuditEntryInfo(String pkField)
    {
        super(pkField);
    }
}