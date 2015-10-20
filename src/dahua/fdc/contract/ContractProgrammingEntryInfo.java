package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractProgrammingEntryInfo extends AbstractContractProgrammingEntryInfo implements Serializable 
{
    public ContractProgrammingEntryInfo()
    {
        super();
    }
    protected ContractProgrammingEntryInfo(String pkField)
    {
        super(pkField);
    }
}