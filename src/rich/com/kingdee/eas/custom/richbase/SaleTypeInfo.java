package com.kingdee.eas.custom.richbase;

import java.io.Serializable;

public class SaleTypeInfo extends AbstractSaleTypeInfo implements Serializable 
{
    public SaleTypeInfo()
    {
        super();
    }
    protected SaleTypeInfo(String pkField)
    {
        super(pkField);
    }
}