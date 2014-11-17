package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;

public class ContractWithoutTextBudgetEntryInfo extends AbstractContractWithoutTextBudgetEntryInfo implements Serializable 
{
    public ContractWithoutTextBudgetEntryInfo()
    {
        super();
    }
    protected ContractWithoutTextBudgetEntryInfo(String pkField)
    {
        super(pkField);
    }
}