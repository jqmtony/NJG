package com.kingdee.eas.xr.xrbase;

import java.io.Serializable;

public class XRBizDataBaseInfo extends AbstractXRBizDataBaseInfo implements Serializable 
{
    public XRBizDataBaseInfo()
    {
        super();
    }
    protected XRBizDataBaseInfo(String pkField)
    {
        super(pkField);
    }
}