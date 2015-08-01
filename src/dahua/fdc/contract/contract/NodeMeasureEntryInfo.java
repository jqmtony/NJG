package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class NodeMeasureEntryInfo extends AbstractNodeMeasureEntryInfo implements Serializable 
{
    public NodeMeasureEntryInfo()
    {
        super();
    }
    protected NodeMeasureEntryInfo(String pkField)
    {
        super(pkField);
    }
}