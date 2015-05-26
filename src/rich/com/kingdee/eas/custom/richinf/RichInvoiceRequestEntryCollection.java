package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichInvoiceRequestEntryCollection extends AbstractObjectCollection 
{
    public RichInvoiceRequestEntryCollection()
    {
        super(RichInvoiceRequestEntryInfo.class);
    }
    public boolean add(RichInvoiceRequestEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichInvoiceRequestEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichInvoiceRequestEntryInfo item)
    {
        return removeObject(item);
    }
    public RichInvoiceRequestEntryInfo get(int index)
    {
        return(RichInvoiceRequestEntryInfo)getObject(index);
    }
    public RichInvoiceRequestEntryInfo get(Object key)
    {
        return(RichInvoiceRequestEntryInfo)getObject(key);
    }
    public void set(int index, RichInvoiceRequestEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichInvoiceRequestEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichInvoiceRequestEntryInfo item)
    {
        return super.indexOf(item);
    }
}