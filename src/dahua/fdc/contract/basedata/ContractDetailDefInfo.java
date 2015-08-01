package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class ContractDetailDefInfo extends AbstractContractDetailDefInfo implements Serializable 
{
    public ContractDetailDefInfo()
    {
        super();
    }
    protected ContractDetailDefInfo(String pkField)
    {
        super(pkField);
    }
}