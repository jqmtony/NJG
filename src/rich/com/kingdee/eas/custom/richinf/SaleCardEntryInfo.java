package com.kingdee.eas.custom.richinf;

import java.io.Serializable;

public class SaleCardEntryInfo extends AbstractSaleCardEntryInfo implements Serializable 
{
    public SaleCardEntryInfo()
    {
        super();
    }
    protected SaleCardEntryInfo(String pkField)
    {
        super(pkField);
    }
}