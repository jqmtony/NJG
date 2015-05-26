package com.kingdee.eas.test;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurOrderEntryCollection extends AbstractObjectCollection 
{
    public PurOrderEntryCollection()
    {
        super(PurOrderEntryInfo.class);
    }
    public boolean add(PurOrderEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurOrderEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurOrderEntryInfo item)
    {
        return removeObject(item);
    }
    public PurOrderEntryInfo get(int index)
    {
        return(PurOrderEntryInfo)getObject(index);
    }
    public PurOrderEntryInfo get(Object key)
    {
        return(PurOrderEntryInfo)getObject(key);
    }
    public void set(int index, PurOrderEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurOrderEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurOrderEntryInfo item)
    {
        return super.indexOf(item);
    }
}