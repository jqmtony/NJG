package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class ProjectStatusInfo extends AbstractProjectStatusInfo implements Serializable 
{
    public ProjectStatusInfo()
    {
        super();
    }
    protected ProjectStatusInfo(String pkField)
    {
        super(pkField);
    }
}