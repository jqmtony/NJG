package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;

public class ContractChangeSettleBillEntryInfo extends AbstractContractChangeSettleBillEntryInfo implements Serializable 
{
    public ContractChangeSettleBillEntryInfo()
    {
        super();
    }
    protected ContractChangeSettleBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}