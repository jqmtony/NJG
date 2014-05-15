package com.kingdee.eas.xr;

import java.io.Serializable;

public class XRBillBaseInfo extends AbstractXRBillBaseInfo implements Serializable 
{
    public XRBillBaseInfo()
    {
        super();
    }
    protected XRBillBaseInfo(String pkField)
    {
        super(pkField);
    }
}