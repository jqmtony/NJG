package com.kingdee.eas.fdc.contract.settle;

import java.io.Serializable;

public class SettleDeclarationBillEntryInfo extends AbstractSettleDeclarationBillEntryInfo implements Serializable 
{
    public SettleDeclarationBillEntryInfo()
    {
        super();
    }
    protected SettleDeclarationBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}