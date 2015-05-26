package com.kingdee.eas.custom.richinf;

import java.io.Serializable;

public class RichExamedInfo extends AbstractRichExamedInfo implements Serializable 
{
    public RichExamedInfo()
    {
        super();
    }
    protected RichExamedInfo(String pkField)
    {
        super(pkField);
    }
}