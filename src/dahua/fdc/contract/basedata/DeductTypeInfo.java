package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class DeductTypeInfo extends AbstractDeductTypeInfo implements Serializable 
{
	
	//水费，电费，罚款等项也应加上
	public static final String partAMaterialType= "fzHvbKPsTCul1P7mjDbGEqiB8+c=";
    public DeductTypeInfo()
    {
        super();
    }
    protected DeductTypeInfo(String pkField)
    {
        super(pkField);
    }
}