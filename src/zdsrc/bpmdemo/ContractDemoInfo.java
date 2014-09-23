package com.kingdee.eas.bpmdemo;

import java.io.Serializable;

public class ContractDemoInfo extends AbstractContractDemoInfo implements Serializable 
{
    public ContractDemoInfo()
    {
        super();
    }
    protected ContractDemoInfo(String pkField)
    {
        super(pkField);
    }
}