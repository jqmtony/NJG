package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CopySupplierEntryCollection extends AbstractObjectCollection 
{
    public CopySupplierEntryCollection()
    {
        super(CopySupplierEntryInfo.class);
    }
    public boolean add(CopySupplierEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CopySupplierEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CopySupplierEntryInfo item)
    {
        return removeObject(item);
    }
    public CopySupplierEntryInfo get(int index)
    {
        return(CopySupplierEntryInfo)getObject(index);
    }
    public CopySupplierEntryInfo get(Object key)
    {
        return(CopySupplierEntryInfo)getObject(key);
    }
    public void set(int index, CopySupplierEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CopySupplierEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CopySupplierEntryInfo item)
    {
        return super.indexOf(item);
    }
}