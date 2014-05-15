package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierInvoiceTypeTreeCollection extends AbstractObjectCollection 
{
    public SupplierInvoiceTypeTreeCollection()
    {
        super(SupplierInvoiceTypeTreeInfo.class);
    }
    public boolean add(SupplierInvoiceTypeTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierInvoiceTypeTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierInvoiceTypeTreeInfo item)
    {
        return removeObject(item);
    }
    public SupplierInvoiceTypeTreeInfo get(int index)
    {
        return(SupplierInvoiceTypeTreeInfo)getObject(index);
    }
    public SupplierInvoiceTypeTreeInfo get(Object key)
    {
        return(SupplierInvoiceTypeTreeInfo)getObject(key);
    }
    public void set(int index, SupplierInvoiceTypeTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierInvoiceTypeTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierInvoiceTypeTreeInfo item)
    {
        return super.indexOf(item);
    }
}