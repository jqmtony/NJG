package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractMoveHistoryInfo extends AbstractContractMoveHistoryInfo implements Serializable 
{
    public ContractMoveHistoryInfo()
    {
        super();
    }
    protected ContractMoveHistoryInfo(String pkField)
    {
        super(pkField);
    }
}