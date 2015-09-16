package com.kingdee.eas.fdc.contract.contractsplit;

import java.io.Serializable;

public class ContractPCSplitBillEntryInfo extends AbstractContractPCSplitBillEntryInfo implements Serializable 
{
    public ContractPCSplitBillEntryInfo()
    {
        super();
    }
    protected ContractPCSplitBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}