package com.kingdee.eas.fdc.costdb;

import java.io.Serializable;

public class FdcMaterialInfo extends AbstractFdcMaterialInfo implements Serializable 
{
    public FdcMaterialInfo()
    {
        super();
    }
    protected FdcMaterialInfo(String pkField)
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