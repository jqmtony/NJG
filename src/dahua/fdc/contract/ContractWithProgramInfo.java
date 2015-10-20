package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractWithProgramInfo extends AbstractContractWithProgramInfo implements Serializable 
{
    public ContractWithProgramInfo()
    {
        super();
    }
    protected ContractWithProgramInfo(String pkField)
    {
        super(pkField);
    }
}