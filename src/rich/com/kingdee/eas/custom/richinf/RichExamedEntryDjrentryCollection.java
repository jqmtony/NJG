package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichExamedEntryDjrentryCollection extends AbstractObjectCollection 
{
    public RichExamedEntryDjrentryCollection()
    {
        super(RichExamedEntryDjrentryInfo.class);
    }
    public boolean add(RichExamedEntryDjrentryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichExamedEntryDjrentryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichExamedEntryDjrentryInfo item)
    {
        return removeObject(item);
    }
    public RichExamedEntryDjrentryInfo get(int index)
    {
        return(RichExamedEntryDjrentryInfo)getObject(index);
    }
    public RichExamedEntryDjrentryInfo get(Object key)
    {
        return(RichExamedEntryDjrentryInfo)getObject(key);
    }
    public void set(int index, RichExamedEntryDjrentryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichExamedEntryDjrentryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichExamedEntryDjrentryInfo item)
    {
        return super.indexOf(item);
    }
}