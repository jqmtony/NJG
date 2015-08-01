package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractModelInfo extends AbstractContractModelInfo implements Serializable 
{
    public ContractModelInfo()
    {
        super();
    }
    protected ContractModelInfo(String pkField)
    {
        super(pkField);
    }
}