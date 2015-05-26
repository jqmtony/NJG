package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichInvoiceRequestCollection extends AbstractObjectCollection 
{
    public RichInvoiceRequestCollection()
    {
        super(RichInvoiceRequestInfo.class);
    }
    public boolean add(RichInvoiceRequestInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichInvoiceRequestCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichInvoiceRequestInfo item)
    {
        return removeObject(item);
    }
    public RichInvoiceRequestInfo get(int index)
    {
        return(RichInvoiceRequestInfo)getObject(index);
    }
    public RichInvoiceRequestInfo get(Object key)
    {
        return(RichInvoiceRequestInfo)getObject(key);
    }
    public void set(int index, RichInvoiceRequestInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichInvoiceRequestInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichInvoiceRequestInfo item)
    {
        return super.indexOf(item);
    }
}