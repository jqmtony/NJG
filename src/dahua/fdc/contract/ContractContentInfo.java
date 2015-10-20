package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractContentInfo extends AbstractContractContentInfo implements Serializable 
{
    public ContractContentInfo()
    {
        super();
    }
    protected ContractContentInfo(String pkField)
    {
        super(pkField);
    }
    public String toString() {
    	if(getFileType()!=null){
    		String reValue=getFileType();
    		reValue = reValue.substring(0, reValue.lastIndexOf("."));
    		return reValue;
    	}
    	
    	return super.toString();
    }
}