package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichExamedEntryCollection extends AbstractObjectCollection 
{
    public RichExamedEntryCollection()
    {
        super(RichExamedEntryInfo.class);
    }
    public boolean add(RichExamedEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichExamedEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichExamedEntryInfo item)
    {
        return removeObject(item);
    }
    public RichExamedEntryInfo get(int index)
    {
        return(RichExamedEntryInfo)getObject(index);
    }
    public RichExamedEntryInfo get(Object key)
    {
        return(RichExamedEntryInfo)getObject(key);
    }
    public void set(int index, RichExamedEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichExamedEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichExamedEntryInfo item)
    {
        return super.indexOf(item);
    }
}