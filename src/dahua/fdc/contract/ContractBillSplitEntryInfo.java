package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractBillSplitEntryInfo extends AbstractContractBillSplitEntryInfo implements Serializable 
{
    public ContractBillSplitEntryInfo()
    {
        super();
    }
    protected ContractBillSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
}