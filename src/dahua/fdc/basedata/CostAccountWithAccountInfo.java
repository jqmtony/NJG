package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class CostAccountWithAccountInfo extends AbstractCostAccountWithAccountInfo implements Serializable 
{
    public CostAccountWithAccountInfo()
    {
        super();
    }
    protected CostAccountWithAccountInfo(String pkField)
    {
        super(pkField);
    }
    //此实体没有编码、名称，因为成本科目与财务科目只能一一对应，故取成本科目编码名称
    public String getLogInfo() {
		String retValue = "";
        if(this.getCostAccount().getNumber()!= null)
        {
            retValue = this.getCostAccount().getNumber();
            if(this.getCostAccount().getName()!=null){
            	retValue = retValue + " " + this.getCostAccount().getName();
            }
        }
        return retValue;
	}
}