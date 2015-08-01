package com.kingdee.eas.fdc.costdb;

import java.io.Serializable;

public class DBAimCostInfo extends AbstractDBAimCostInfo implements Serializable 
{
    public DBAimCostInfo()
    {
        super();
    }
    protected DBAimCostInfo(String pkField)
    {
        super(pkField);
    }
    public String getLogInfo() {
		String retValue = "";
        if(this.getNumber()!= null)
        {
            retValue = this.getNumber();          
        }
        return retValue;
	}
}