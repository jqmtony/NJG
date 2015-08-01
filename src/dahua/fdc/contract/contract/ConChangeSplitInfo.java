package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ConChangeSplitInfo extends AbstractConChangeSplitInfo implements Serializable 
{
    public ConChangeSplitInfo()
    {
        super();
    }
    protected ConChangeSplitInfo(String pkField)
    {
        super(pkField);
    }
    public String getLogInfo() {
		String retValue = "";
        if(this.getContractChange()!=null&&this.getContractChange().getNumber()!= null)
        {
            retValue = this.getContractChange().getNumber();
            if(this.getContractChange().getName()!=null){
            	retValue = retValue + " " + this.getContractChange().getName();
            }
        }
        return retValue;
	}
}