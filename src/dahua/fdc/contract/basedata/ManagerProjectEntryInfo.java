package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class ManagerProjectEntryInfo extends AbstractManagerProjectEntryInfo implements Serializable 
{
    public ManagerProjectEntryInfo()
    {
        super();
    }
    protected ManagerProjectEntryInfo(String pkField)
    {
        super(pkField);
    }
    
    public String toString() {
    	if(getCurProject()==null){
    		return super.toString();
    	}
    	
    	String displayName = getCurProject().getDisplayName();
    	if(displayName!=null){
    		displayName=displayName.replaceAll("_", "/");
    		return displayName;
    	}else{
    		return this.getCurProject().getName();
    	}
    }
}