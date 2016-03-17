package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SwbEntryCollection extends AbstractObjectCollection 
{
    public SwbEntryCollection()
    {
        super(SwbEntryInfo.class);
    }
    public boolean add(SwbEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SwbEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SwbEntryInfo item)
    {
        return removeObject(item);
    }
    public SwbEntryInfo get(int index)
    {
        return(SwbEntryInfo)getObject(index);
    }
    public SwbEntryInfo get(Object key)
    {
        return(SwbEntryInfo)getObject(key);
    }
    public void set(int index, SwbEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SwbEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SwbEntryInfo item)
    {
        return super.indexOf(item);
    }
}