package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;

public class ContractBillBudgetEntryInfo extends AbstractContractBillBudgetEntryInfo implements Serializable 
{
    public ContractBillBudgetEntryInfo()
    {
        super();
    }
    protected ContractBillBudgetEntryInfo(String pkField)
    {
        super(pkField);
    }
}