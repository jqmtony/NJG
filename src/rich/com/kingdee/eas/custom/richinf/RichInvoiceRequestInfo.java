package com.kingdee.eas.custom.richinf;

import java.io.Serializable;

public class RichInvoiceRequestInfo extends AbstractRichInvoiceRequestInfo implements Serializable 
{
    public RichInvoiceRequestInfo()
    {
        super();
    }
    protected RichInvoiceRequestInfo(String pkField)
    {
        super(pkField);
    }
}