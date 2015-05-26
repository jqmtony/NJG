package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SaleCardEntryCollection extends AbstractObjectCollection 
{
    public SaleCardEntryCollection()
    {
        super(SaleCardEntryInfo.class);
    }
    public boolean add(SaleCardEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SaleCardEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SaleCardEntryInfo item)
    {
        return removeObject(item);
    }
    public SaleCardEntryInfo get(int index)
    {
        return(SaleCardEntryInfo)getObject(index);
    }
    public SaleCardEntryInfo get(Object key)
    {
        return(SaleCardEntryInfo)getObject(key);
    }
    public void set(int index, SaleCardEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SaleCardEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SaleCardEntryInfo item)
    {
        return super.indexOf(item);
    }
}