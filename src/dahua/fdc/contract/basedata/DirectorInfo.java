package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class DirectorInfo extends AbstractDirectorInfo implements Serializable 
{
    public DirectorInfo()
    {
        super();
    }
    protected DirectorInfo(String pkField)
    {
        super(pkField);
    }
}