package com.kingdee.eas.port.pm.acceptance;

import java.io.Serializable;

public class CompleteAcceptInfo extends AbstractCompleteAcceptInfo implements Serializable 
{
    public CompleteAcceptInfo()
    {
        super();
    }
    protected CompleteAcceptInfo(String pkField)
    {
        super(pkField);
    }
}