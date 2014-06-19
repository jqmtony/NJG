package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractContentInfo extends AbstractContractContentInfo implements Serializable 
{
    public ContractContentInfo()
    {
        super();
    }
    protected ContractContentInfo(String pkField)
    {
        super(pkField);
    }
}