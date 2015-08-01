package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class EASLogTimeInfo extends AbstractEASLogTimeInfo implements Serializable 
{
    public EASLogTimeInfo()
    {
        super();
    }
    protected EASLogTimeInfo(String pkField)
    {
        super(pkField);
    }
}