package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierLevelCollection extends AbstractObjectCollection 
{
    public SupplierLevelCollection()
    {
        super(SupplierLevelInfo.class);
    }
    public boolean add(SupplierLevelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierLevelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierLevelInfo item)
    {
        return removeObject(item);
    }
    public SupplierLevelInfo get(int index)
    {
        return(SupplierLevelInfo)getObject(index);
    }
    public SupplierLevelInfo get(Object key)
    {
        return(SupplierLevelInfo)getObject(key);
    }
    public void set(int index, SupplierLevelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierLevelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierLevelInfo item)
    {
        return super.indexOf(item);
    }
}