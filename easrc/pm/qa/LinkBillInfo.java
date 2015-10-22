package com.kingdee.eas.port.pm.qa;

import java.io.Serializable;

public class LinkBillInfo extends AbstractLinkBillInfo implements Serializable 
{
    public LinkBillInfo()
    {
        super();
    }
    protected LinkBillInfo(String pkField)
    {
        super(pkField);
    }
}