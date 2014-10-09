package com.kingdee.eas.bpmdemo;

import java.io.Serializable;

public class ChangeVisaAppEntryInfo extends AbstractChangeVisaAppEntryInfo implements Serializable 
{
    public ChangeVisaAppEntryInfo()
    {
        super();
    }
    protected ChangeVisaAppEntryInfo(String pkField)
    {
        super(pkField);
    }
}