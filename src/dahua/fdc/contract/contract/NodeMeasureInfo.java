package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class NodeMeasureInfo extends AbstractNodeMeasureInfo implements Serializable 
{
    public NodeMeasureInfo()
    {
        super();
    }
    protected NodeMeasureInfo(String pkField)
    {
        super(pkField);
    }
}