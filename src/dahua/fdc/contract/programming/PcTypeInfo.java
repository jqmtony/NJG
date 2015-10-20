package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;

public class PcTypeInfo extends AbstractPcTypeInfo implements Serializable 
{
    public PcTypeInfo()
    {
        super();
    }
    protected PcTypeInfo(String pkField)
    {
        super(pkField);
    }
}