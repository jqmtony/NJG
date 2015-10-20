package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractBailInfo extends AbstractContractBailInfo implements Serializable 
{
    public ContractBailInfo()
    {
        super();
    }
    protected ContractBailInfo(String pkField)
    {
        super(pkField);
    }
}