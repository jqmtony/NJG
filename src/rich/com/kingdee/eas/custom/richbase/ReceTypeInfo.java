package com.kingdee.eas.custom.richbase;

import java.io.Serializable;

public class ReceTypeInfo extends AbstractReceTypeInfo implements Serializable 
{
    public ReceTypeInfo()
    {
        super();
    }
    protected ReceTypeInfo(String pkField)
    {
        super(pkField);
    }
}