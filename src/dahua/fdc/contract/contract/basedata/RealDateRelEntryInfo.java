package com.kingdee.eas.fdc.contract.basedata;

import java.io.Serializable;

public class RealDateRelEntryInfo extends AbstractRealDateRelEntryInfo implements Serializable 
{
    public RealDateRelEntryInfo()
    {
        super();
    }
    protected RealDateRelEntryInfo(String pkField)
    {
        super(pkField);
    }
}