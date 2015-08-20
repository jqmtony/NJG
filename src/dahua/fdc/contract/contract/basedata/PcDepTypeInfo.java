package com.kingdee.eas.fdc.contract.basedata;

import java.io.Serializable;

public class PcDepTypeInfo extends AbstractPcDepTypeInfo implements Serializable 
{
    public PcDepTypeInfo()
    {
        super();
    }
    protected PcDepTypeInfo(String pkField)
    {
        super(pkField);
    }
}