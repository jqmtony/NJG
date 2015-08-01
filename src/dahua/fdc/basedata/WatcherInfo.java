package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class WatcherInfo extends AbstractWatcherInfo implements Serializable 
{
    public WatcherInfo()
    {
        super();
    }
    protected WatcherInfo(String pkField)
    {
        super(pkField);
    }
}