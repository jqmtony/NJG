package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class ProjectIndexDataInfo extends AbstractProjectIndexDataInfo implements Serializable 
{
    public ProjectIndexDataInfo()
    {
        super();
    }
    protected ProjectIndexDataInfo(String pkField)
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