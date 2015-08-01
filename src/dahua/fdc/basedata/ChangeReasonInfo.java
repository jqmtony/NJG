package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class ChangeReasonInfo extends AbstractChangeReasonInfo implements Serializable 
{
    public ChangeReasonInfo()
    {
        super();
    }
    protected ChangeReasonInfo(String pkField)
    {
        super(pkField);
    }
}