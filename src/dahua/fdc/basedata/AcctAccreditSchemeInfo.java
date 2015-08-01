package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class AcctAccreditSchemeInfo extends AbstractAcctAccreditSchemeInfo implements Serializable 
{
    public AcctAccreditSchemeInfo()
    {
        super();
    }
    protected AcctAccreditSchemeInfo(String pkField)
    {
        super(pkField);
    }
    public String toString() {
    	return this.getName();
    }
}