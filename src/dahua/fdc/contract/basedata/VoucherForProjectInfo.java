package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class VoucherForProjectInfo extends AbstractVoucherForProjectInfo implements Serializable 
{
    public VoucherForProjectInfo()
    {
        super();
    }
    protected VoucherForProjectInfo(String pkField)
    {
        super(pkField);
    }
}