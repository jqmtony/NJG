package com.kingdee.eas.bpm;

import java.io.Serializable;

public class BPMLogInfo extends AbstractBPMLogInfo implements Serializable 
{
    public BPMLogInfo()
    {
        super();
    }
    protected BPMLogInfo(String pkField)
    {
        super(pkField);
    }
}