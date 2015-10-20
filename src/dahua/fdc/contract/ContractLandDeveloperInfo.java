package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractLandDeveloperInfo extends AbstractContractLandDeveloperInfo implements Serializable 
{
    public ContractLandDeveloperInfo()
    {
        super();
    }
    protected ContractLandDeveloperInfo(String pkField)
    {
        super(pkField);
    }
}