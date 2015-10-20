package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractChangeEntryInfo extends AbstractContractChangeEntryInfo implements Serializable 
{
    public ContractChangeEntryInfo()
    {
        super();
    }
    protected ContractChangeEntryInfo(String pkField)
    {
        super(pkField);
    }
}