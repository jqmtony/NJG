package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ChangeAuditBillInfo extends AbstractChangeAuditBillInfo implements Serializable 
{
    public ChangeAuditBillInfo()
    {
        super();
    }
    protected ChangeAuditBillInfo(String pkField)
    {
        super(pkField);
    }
}