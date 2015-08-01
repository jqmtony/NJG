package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EntryDescCollection extends AbstractObjectCollection 
{
    public EntryDescCollection()
    {
        super(EntryDescInfo.class);
    }
    public boolean add(EntryDescInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EntryDescCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EntryDescInfo item)
    {
        return removeObject(item);
    }
    public EntryDescInfo get(int index)
    {
        return(EntryDescInfo)getObject(index);
    }
    public EntryDescInfo get(Object key)
    {
        return(EntryDescInfo)getObject(key);
    }
    public void set(int index, EntryDescInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EntryDescInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EntryDescInfo item)
    {
        return super.indexOf(item);
    }
}