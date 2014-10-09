package com.kingdee.eas.bpmdemo;

import java.io.Serializable;

public class ChangeVisaAppInfo extends AbstractChangeVisaAppInfo implements Serializable 
{
    public ChangeVisaAppInfo()
    {
        super();
    }
    protected ChangeVisaAppInfo(String pkField)
    {
        super(pkField);
    }
}