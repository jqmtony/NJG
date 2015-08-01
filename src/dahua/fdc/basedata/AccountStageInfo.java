package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class AccountStageInfo extends AbstractAccountStageInfo implements Serializable 
{
    public AccountStageInfo()
    {
        super();
    }
    protected AccountStageInfo(String pkField)
    {
        super(pkField);
    }
}