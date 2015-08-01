package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class BITargetInfo extends AbstractBITargetInfo implements Serializable 
{
    public BITargetInfo()
    {
        super();
    }
    protected BITargetInfo(String pkField)
    {
        super(pkField);
    }
}