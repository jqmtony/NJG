package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;

public class ContractBillPayItemInfo extends AbstractContractBillPayItemInfo implements Serializable 
{
    public ContractBillPayItemInfo()
    {
        super();
    }
    protected ContractBillPayItemInfo(String pkField)
    {
        super(pkField);
    }
}