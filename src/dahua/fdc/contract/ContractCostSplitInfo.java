package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractCostSplitInfo extends AbstractContractCostSplitInfo implements Serializable 
{
    public ContractCostSplitInfo()
    {
        super();
    }
    protected ContractCostSplitInfo(String pkField)
    {
        super(pkField);
    }
    public String getLogInfo() {
		String retValue = "";
        if(this.getContractBill()!=null&&this.getContractBill().getNumber()!= null)
        {
            retValue = this.getContractBill().getNumber();
            if(this.getContractBill().getName()!=null){
            	retValue = retValue + " " + this.getContractBill().getName();
            }
        }
        return retValue;
	}
}