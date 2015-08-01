package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class TaxManagerInfo extends AbstractTaxManagerInfo implements Serializable 
{
    public TaxManagerInfo()
    {
        super();
    }
    protected TaxManagerInfo(String pkField)
    {
        super(pkField);
    }
}