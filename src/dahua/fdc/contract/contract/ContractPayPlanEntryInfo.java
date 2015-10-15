package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractPayPlanEntryInfo extends AbstractContractPayPlanEntryInfo implements Serializable 
{
    public ContractPayPlanEntryInfo()
    {
        super();
    }
    protected ContractPayPlanEntryInfo(String pkField)
    {
        super(pkField);
    }
}