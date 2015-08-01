package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class MeasureIndexInfo extends AbstractMeasureIndexInfo implements Serializable 
{
    public MeasureIndexInfo()
    {
        super();
    }
    protected MeasureIndexInfo(String pkField)
    {
        super(pkField);
    }
}