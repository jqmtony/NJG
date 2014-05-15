package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;

public class ContractWithoutTextBgEntryInfo extends AbstractContractWithoutTextBgEntryInfo implements Serializable 
{
    public ContractWithoutTextBgEntryInfo()
    {
        super();
    }
    protected ContractWithoutTextBgEntryInfo(String pkField)
    {
        super(pkField);
    }
}