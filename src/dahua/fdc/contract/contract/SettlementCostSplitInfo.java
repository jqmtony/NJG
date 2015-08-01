package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class SettlementCostSplitInfo extends AbstractSettlementCostSplitInfo implements Serializable 
{
    public SettlementCostSplitInfo()
    {
        super();
    }
    protected SettlementCostSplitInfo(String pkField)
    {
        super(pkField);
    }
    public String getLogInfo() {
		String retValue = "";
        if(this.getSettlementBill()!=null&&this.getSettlementBill().getNumber()!= null)
        {
            retValue = this.getSettlementBill().getNumber();
            if(this.getSettlementBill().getName()!=null){
            	retValue = retValue + " " + this.getSettlementBill().getName();
            }
        }
        return retValue;
	}
}