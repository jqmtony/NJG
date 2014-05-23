package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;

public class ProjectStartRequestInfo extends AbstractProjectStartRequestInfo implements Serializable 
{
    public ProjectStartRequestInfo()
    {
        super();
    }
    protected ProjectStartRequestInfo(String pkField)
    {
        super(pkField);
    }
}