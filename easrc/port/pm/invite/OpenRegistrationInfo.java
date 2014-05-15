package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;

public class OpenRegistrationInfo extends AbstractOpenRegistrationInfo implements Serializable 
{
    public OpenRegistrationInfo()
    {
        super();
    }
    protected OpenRegistrationInfo(String pkField)
    {
        super(pkField);
    }
}