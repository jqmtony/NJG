package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class BuildTypeInfo extends AbstractBuildTypeInfo implements Serializable 
{
    public BuildTypeInfo()
    {
        super();
    }
    protected BuildTypeInfo(String pkField)
    {
        super(pkField);
    }
}