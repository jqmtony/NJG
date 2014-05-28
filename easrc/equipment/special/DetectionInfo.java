package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;

public class DetectionInfo extends AbstractDetectionInfo implements Serializable 
{
    public DetectionInfo()
    {
        super();
    }
    protected DetectionInfo(String pkField)
    {
        super(pkField);
    }
}