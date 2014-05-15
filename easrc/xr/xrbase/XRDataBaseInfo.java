package com.kingdee.eas.xr.xrbase;

import java.io.Serializable;

public class XRDataBaseInfo extends AbstractXRDataBaseInfo implements Serializable 
{
    public XRDataBaseInfo()
    {
        super();
    }
    protected XRDataBaseInfo(String pkField)
    {
        super(pkField);
    }
}