package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractProgrammingInfo extends AbstractContractProgrammingInfo implements Serializable 
{
    public ContractProgrammingInfo()
    {
        super();
    }
    protected ContractProgrammingInfo(String pkField)
    {
        super(pkField);
    }
}