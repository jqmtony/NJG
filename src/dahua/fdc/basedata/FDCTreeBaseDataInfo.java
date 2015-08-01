package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class FDCTreeBaseDataInfo extends AbstractFDCTreeBaseDataInfo implements Serializable 
{
    public FDCTreeBaseDataInfo()
    {
        super();
    }
    protected FDCTreeBaseDataInfo(String pkField)
    {
        super(pkField);
    }
}