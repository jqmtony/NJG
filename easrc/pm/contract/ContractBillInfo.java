package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;

public class ContractBillInfo extends AbstractContractBillInfo implements Serializable 
{
    public ContractBillInfo()
    {
        super();
    }
    protected ContractBillInfo(String pkField)
    {
        super(pkField);
    }
}