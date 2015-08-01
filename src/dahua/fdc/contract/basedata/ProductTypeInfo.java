package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class ProductTypeInfo extends AbstractProductTypeInfo implements Serializable 
{
    public ProductTypeInfo()
    {
        super();
    }
    protected ProductTypeInfo(String pkField)
    {
        super(pkField);
    }
}