package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class PayReqPrjPayEntryInfo extends AbstractPayReqPrjPayEntryInfo implements Serializable 
{
    public PayReqPrjPayEntryInfo()
    {
        super();
    }
    protected PayReqPrjPayEntryInfo(String pkField)
    {
        super(pkField);
    }
}