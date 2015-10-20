package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractCostSplitEntryInfo extends AbstractContractCostSplitEntryInfo implements Serializable 
{
    public ContractCostSplitEntryInfo()
    {
        super();
    }
    protected ContractCostSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
}