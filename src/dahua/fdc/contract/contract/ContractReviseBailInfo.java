package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractReviseBailInfo extends AbstractContractReviseBailInfo implements Serializable 
{
    public ContractReviseBailInfo()
    {
        super();
    }
    protected ContractReviseBailInfo(String pkField)
    {
        super(pkField);
    }
}