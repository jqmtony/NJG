package com.kingdee.eas.port.markesupplier.subase;

import java.io.Serializable;

public class VisibilityInfo extends AbstractVisibilityInfo implements Serializable 
{
    public VisibilityInfo()
    {
        super();
    }
    protected VisibilityInfo(String pkField)
    {
        super(pkField);
    }
}