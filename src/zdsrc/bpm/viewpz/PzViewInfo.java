package com.kingdee.eas.bpm.viewpz;

import java.io.Serializable;

public class PzViewInfo extends AbstractPzViewInfo implements Serializable 
{
    public PzViewInfo()
    {
        super();
    }
    protected PzViewInfo(String pkField)
    {
        super(pkField);
    }
}