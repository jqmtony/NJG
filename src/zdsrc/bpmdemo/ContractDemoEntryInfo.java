package com.kingdee.eas.bpmdemo;

import java.io.Serializable;

public class ContractDemoEntryInfo extends AbstractContractDemoEntryInfo implements Serializable 
{
    public ContractDemoEntryInfo()
    {
        super();
    }
    protected ContractDemoEntryInfo(String pkField)
    {
        super(pkField);
    }
}