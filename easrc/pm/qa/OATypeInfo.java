package com.kingdee.eas.port.pm.qa;

import java.io.Serializable;

public class OATypeInfo extends AbstractOATypeInfo implements Serializable 
{
    public OATypeInfo()
    {
        super();
    }
    protected OATypeInfo(String pkField)
    {
        super(pkField);
    }
}