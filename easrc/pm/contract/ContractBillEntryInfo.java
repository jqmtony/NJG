package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;

public class ContractBillEntryInfo extends AbstractContractBillEntryInfo implements Serializable 
{
    public ContractBillEntryInfo()
    {
        super();
    }
    protected ContractBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}