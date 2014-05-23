package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierInvoiceTypeCollection extends AbstractObjectCollection 
{
    public SupplierInvoiceTypeCollection()
    {
        super(SupplierInvoiceTypeInfo.class);
    }
    public boolean add(SupplierInvoiceTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierInvoiceTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierInvoiceTypeInfo item)
    {
        return removeObject(item);
    }
    public SupplierInvoiceTypeInfo get(int index)
    {
        return(SupplierInvoiceTypeInfo)getObject(index);
    }
    public SupplierInvoiceTypeInfo get(Object key)
    {
        return(SupplierInvoiceTypeInfo)getObject(key);
    }
    public void set(int index, SupplierInvoiceTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierInvoiceTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierInvoiceTypeInfo item)
    {
        return super.indexOf(item);
    }
}