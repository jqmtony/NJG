package com.kingdee.eas.fdc.basedata.mobile;

import java.io.Serializable;

public class FdcMobileBillInfo extends AbstractFdcMobileBillInfo implements Serializable 
{
    public FdcMobileBillInfo()
    {
        super();
    }
    protected FdcMobileBillInfo(String pkField)
    {
        super(pkField);
    }
}