package com.kingdee.eas.custom.richinf;

import java.io.Serializable;

public class RichInvoiceRequestEntryInfo extends AbstractRichInvoiceRequestEntryInfo implements Serializable 
{
    public RichInvoiceRequestEntryInfo()
    {
        super();
    }
    protected RichInvoiceRequestEntryInfo(String pkField)
    {
        super(pkField);
    }
}