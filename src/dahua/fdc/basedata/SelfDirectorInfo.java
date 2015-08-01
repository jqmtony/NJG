package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class SelfDirectorInfo extends AbstractSelfDirectorInfo implements Serializable 
{
    public SelfDirectorInfo()
    {
        super();
    }
    protected SelfDirectorInfo(String pkField)
    {
        super(pkField);
    }
}