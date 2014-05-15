package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class PortProjectTypeInfo extends AbstractPortProjectTypeInfo implements Serializable 
{
    public PortProjectTypeInfo()
    {
        super();
    }
    protected PortProjectTypeInfo(String pkField)
    {
        super(pkField);
    }
}