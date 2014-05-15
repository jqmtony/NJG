package com.kingdee.eas.port.pm.project;

import java.io.Serializable;

public class PortProjectInfo extends AbstractPortProjectInfo implements Serializable 
{
    public PortProjectInfo()
    {
        super();
    }
    protected PortProjectInfo(String pkField)
    {
        super(pkField);
    }
}