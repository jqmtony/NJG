package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class ProjectTypeInfo extends AbstractProjectTypeInfo implements Serializable 
{
    public ProjectTypeInfo()
    {
        super();
    }
    protected ProjectTypeInfo(String pkField)
    {
        super(pkField);
    }
}