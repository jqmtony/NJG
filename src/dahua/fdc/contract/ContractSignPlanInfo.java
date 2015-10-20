package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractSignPlanInfo extends AbstractContractSignPlanInfo implements Serializable 
{
    public ContractSignPlanInfo()
    {
        super();
    }
    protected ContractSignPlanInfo(String pkField)
    {
        super(pkField);
    }
}