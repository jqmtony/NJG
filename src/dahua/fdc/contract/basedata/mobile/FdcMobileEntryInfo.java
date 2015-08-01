package com.kingdee.eas.fdc.basedata.mobile;

import java.io.Serializable;

public class FdcMobileEntryInfo extends AbstractFdcMobileEntryInfo implements Serializable 
{
    public FdcMobileEntryInfo()
    {
        super();
    }
    protected FdcMobileEntryInfo(String pkField)
    {
        super(pkField);
    }
}