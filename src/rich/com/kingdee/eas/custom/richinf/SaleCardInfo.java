package com.kingdee.eas.custom.richinf;

import java.io.Serializable;

public class SaleCardInfo extends AbstractSaleCardInfo implements Serializable 
{
    public SaleCardInfo()
    {
        super();
    }
    protected SaleCardInfo(String pkField)
    {
        super(pkField);
    }
}