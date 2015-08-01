package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractReviseBailEntryInfo extends AbstractContractReviseBailEntryInfo implements Serializable 
{
    public ContractReviseBailEntryInfo()
    {
        super();
    }
    protected ContractReviseBailEntryInfo(String pkField)
    {
        super(pkField);
    }
}