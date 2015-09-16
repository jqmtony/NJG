package com.kingdee.eas.fdc.contract.contractsplit;

import java.io.Serializable;

public class ContractPCSplitBillInfo extends AbstractContractPCSplitBillInfo implements Serializable 
{
    public ContractPCSplitBillInfo()
    {
        super();
    }
    protected ContractPCSplitBillInfo(String pkField)
    {
        super(pkField);
    }
}