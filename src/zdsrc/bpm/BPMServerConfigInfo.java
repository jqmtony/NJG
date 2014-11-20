package com.kingdee.eas.bpm;

import java.io.Serializable;

public class BPMServerConfigInfo extends AbstractBPMServerConfigInfo implements Serializable 
{
    public BPMServerConfigInfo()
    {
        super();
    }
    protected BPMServerConfigInfo(String pkField)
    {
        super(pkField);
    }
}