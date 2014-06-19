package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractBailEntryInfo extends AbstractContractBailEntryInfo implements Serializable 
{
    public ContractBailEntryInfo()
    {
        super();
    }
    protected ContractBailEntryInfo(String pkField)
    {
        super(pkField);
    }
}