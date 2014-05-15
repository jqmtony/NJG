package com.kingdee.eas.port.pm.contract.basedata;

import java.io.Serializable;

public class ContractTypeEntryInfo extends AbstractContractTypeEntryInfo implements Serializable 
{
    public ContractTypeEntryInfo()
    {
        super();
    }
    protected ContractTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
}