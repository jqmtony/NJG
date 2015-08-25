package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ChangeAuditBillSplitEntryInfo extends AbstractChangeAuditBillSplitEntryInfo implements Serializable 
{
    public ChangeAuditBillSplitEntryInfo()
    {
        super();
    }
    protected ChangeAuditBillSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
}