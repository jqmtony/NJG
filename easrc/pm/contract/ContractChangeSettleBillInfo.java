package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;

public class ContractChangeSettleBillInfo extends AbstractContractChangeSettleBillInfo implements Serializable 
{
    public ContractChangeSettleBillInfo()
    {
        super();
    }
    protected ContractChangeSettleBillInfo(String pkField)
    {
        super(pkField);
    }
}