package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractPayItemInfo extends AbstractContractPayItemInfo implements Serializable 
{
    public ContractPayItemInfo()
    {
        super();
    }
    protected ContractPayItemInfo(String pkField)
    {
        super(pkField);
    }
}