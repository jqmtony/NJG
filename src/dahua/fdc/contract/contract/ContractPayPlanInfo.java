package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractPayPlanInfo extends AbstractContractPayPlanInfo implements Serializable 
{
    public ContractPayPlanInfo()
    {
        super();
    }
    protected ContractPayPlanInfo(String pkField)
    {
        super(pkField);
    }
}