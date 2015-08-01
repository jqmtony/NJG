package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class LandDeveloperInfo extends AbstractLandDeveloperInfo implements Serializable 
{
    public LandDeveloperInfo()
    {
        super();
    }
    protected LandDeveloperInfo(String pkField)
    {
        super(pkField);
    }
}