package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;

public class OpenRegistrationEntryInfo extends AbstractOpenRegistrationEntryInfo implements Serializable 
{
    public OpenRegistrationEntryInfo()
    {
        super();
    }
    protected OpenRegistrationEntryInfo(String pkField)
    {
        super(pkField);
    }
}