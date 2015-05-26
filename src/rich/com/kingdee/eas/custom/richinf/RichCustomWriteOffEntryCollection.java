package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RichCustomWriteOffEntryCollection extends AbstractObjectCollection 
{
    public RichCustomWriteOffEntryCollection()
    {
        super(RichCustomWriteOffEntryInfo.class);
    }
    public boolean add(RichCustomWriteOffEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RichCustomWriteOffEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RichCustomWriteOffEntryInfo item)
    {
        return removeObject(item);
    }
    public RichCustomWriteOffEntryInfo get(int index)
    {
        return(RichCustomWriteOffEntryInfo)getObject(index);
    }
    public RichCustomWriteOffEntryInfo get(Object key)
    {
        return(RichCustomWriteOffEntryInfo)getObject(key);
    }
    public void set(int index, RichCustomWriteOffEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RichCustomWriteOffEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RichCustomWriteOffEntryInfo item)
    {
        return super.indexOf(item);
    }
}