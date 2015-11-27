package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class GcftbEntryCollection extends AbstractObjectCollection 
{
    public GcftbEntryCollection()
    {
        super(GcftbEntryInfo.class);
    }
    public boolean add(GcftbEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(GcftbEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(GcftbEntryInfo item)
    {
        return removeObject(item);
    }
    public GcftbEntryInfo get(int index)
    {
        return(GcftbEntryInfo)getObject(index);
    }
    public GcftbEntryInfo get(Object key)
    {
        return(GcftbEntryInfo)getObject(key);
    }
    public void set(int index, GcftbEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(GcftbEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(GcftbEntryInfo item)
    {
        return super.indexOf(item);
    }
}