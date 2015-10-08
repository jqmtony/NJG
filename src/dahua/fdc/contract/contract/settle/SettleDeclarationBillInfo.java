package com.kingdee.eas.fdc.contract.settle;

import java.io.Serializable;

public class SettleDeclarationBillInfo extends AbstractSettleDeclarationBillInfo implements Serializable 
{
    public SettleDeclarationBillInfo()
    {
        super();
    }
    protected SettleDeclarationBillInfo(String pkField)
    {
        super(pkField);
    }
}