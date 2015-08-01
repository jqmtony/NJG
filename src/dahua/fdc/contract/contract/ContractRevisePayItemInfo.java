package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractRevisePayItemInfo extends AbstractContractRevisePayItemInfo implements Serializable 
{
    public ContractRevisePayItemInfo()
    {
        super();
    }
    protected ContractRevisePayItemInfo(String pkField)
    {
        super(pkField);
    }
}