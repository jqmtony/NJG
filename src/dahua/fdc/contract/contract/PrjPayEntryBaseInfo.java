package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class PrjPayEntryBaseInfo extends AbstractPrjPayEntryBaseInfo implements Serializable 
{
    public PrjPayEntryBaseInfo()
    {
        super();
    }
    protected PrjPayEntryBaseInfo(String pkField)
    {
        super(pkField);
    }
}