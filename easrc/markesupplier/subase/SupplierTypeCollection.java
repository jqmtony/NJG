package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierTypeCollection extends AbstractObjectCollection 
{
    public SupplierTypeCollection()
    {
        super(SupplierTypeInfo.class);
    }
    public boolean add(SupplierTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierTypeInfo item)
    {
        return removeObject(item);
    }
    public SupplierTypeInfo get(int index)
    {
        return(SupplierTypeInfo)getObject(index);
    }
    public SupplierTypeInfo get(Object key)
    {
        return(SupplierTypeInfo)getObject(key);
    }
    public void set(int index, SupplierTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierTypeInfo item)
    {
        return super.indexOf(item);
    }
}