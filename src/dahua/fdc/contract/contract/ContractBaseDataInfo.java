package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractBaseDataInfo extends AbstractContractBaseDataInfo implements Serializable 
{
    public ContractBaseDataInfo()
    {
        super();
    }
    protected ContractBaseDataInfo(String pkField)
    {
        super(pkField);
    }
}