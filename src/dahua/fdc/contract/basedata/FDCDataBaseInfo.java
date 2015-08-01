package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class FDCDataBaseInfo extends AbstractFDCDataBaseInfo implements Serializable 
{
    public FDCDataBaseInfo()
    {
        super();
    }
    protected FDCDataBaseInfo(String pkField)
    {
        super(pkField);
    }
    
    public String getLogInfo() {
		String retValue = "";
        if(this.getNumber()!= null)
        {
            retValue = this.getNumber();
            if(this.getName()!=null){
            	retValue = retValue + " " + this.getName();
            }
        }
        return retValue;
	}
}