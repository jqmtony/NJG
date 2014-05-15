package com.kingdee.eas.port.pm.contract.basedata;

import java.io.Serializable;

public class PayContentTypeInfo extends AbstractPayContentTypeInfo implements Serializable 
{
    public PayContentTypeInfo()
    {
        super();
    }
    protected PayContentTypeInfo(String pkField)
    {
        super(pkField);
    }
}