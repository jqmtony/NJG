package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierContentEntryCollection extends AbstractObjectCollection 
{
    public SupplierContentEntryCollection()
    {
        super(SupplierContentEntryInfo.class);
    }
    public boolean add(SupplierContentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierContentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierContentEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierContentEntryInfo get(int index)
    {
        return(SupplierContentEntryInfo)getObject(index);
    }
    public SupplierContentEntryInfo get(Object key)
    {
        return(SupplierContentEntryInfo)getObject(key);
    }
    public void set(int index, SupplierContentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierContentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierContentEntryInfo item)
    {
        return super.indexOf(item);
    }
}