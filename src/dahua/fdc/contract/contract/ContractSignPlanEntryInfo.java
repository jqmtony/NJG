package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

import com.kingdee.eas.fi.ap.AbstractApInvoiceEntryInfo;

public class ContractSignPlanEntryInfo extends AbstractApInvoiceEntryInfo implements Serializable 
{
    public ContractSignPlanEntryInfo()
    {
        super();
    }
    protected ContractSignPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
}